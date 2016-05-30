/*
 * please DO NOT PUSH this file to fastr/master without further discussion.
 *
 * example command line:
 * mx --J @'-Dgraal.Dump=HighTier:1 -Dgraal.MethodFilter=*TestCasts* -Dgraal.TraceTruffleCompilation=true -Dgraal.PrintBackendCFG=false'  junits --tests TestCasts
 *
 * of course, Graal needs to be imported for this to work:
 * DEFAULT_DYNAMIC_IMPORTS=graal-core (or graal-enterprise)
 */
package com.oracle.truffle.r.nodes.casts;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.r.nodes.builtin.CastBuilder;
import com.oracle.truffle.r.nodes.builtin.ValuePredicateArgumentFilter;
import com.oracle.truffle.r.nodes.test.TestBase;
import com.oracle.truffle.r.nodes.unary.CastNode;
import com.oracle.truffle.r.runtime.RError.Message;
import com.oracle.truffle.r.runtime.RRuntime;
import com.oracle.truffle.r.runtime.context.RContext;
import com.oracle.truffle.r.runtime.data.RDataFactory;
import com.oracle.truffle.r.runtime.data.RNull;
import com.oracle.truffle.r.runtime.nodes.RSyntaxNode;

import static com.oracle.truffle.r.nodes.builtin.CastBuilder.Predef.*;

public class TestCasts extends TestBase {

    private static final int TIMEOUT = 15000;

    private abstract static class TestRootNode<T extends Node> extends RootNode {

        private static final FrameDescriptor descriptor = new FrameDescriptor();
        private final String name;
        private boolean isCompiled = false;
        @Child protected T node;

        protected TestRootNode(String name, T node) {
            super(RContext.getRForeignAccessFactory().getTruffleLanguage(), RSyntaxNode.INTERNAL, descriptor);
            this.name = name;
            this.node = node;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object result = execute(frame, frame.getArguments()[0]);
            if (CompilerDirectives.inCompiledCode()) {
                isCompiled = true;
            } else {
                isCompiled = false;
            }
            return result;
        }

        protected abstract Object execute(VirtualFrame frame, Object value);

        @Override
        public String toString() {
            return "TestCasts" + name;
        }
    }

    private static void testCompilation(Object[] values, TestRootNode<?> root, Object... deOptVals) {
        RootCallTarget target = Truffle.getRuntime().createCallTarget(root);

        long timeout = System.currentTimeMillis() + TIMEOUT;
        int i = 0;
        while (System.currentTimeMillis() < timeout) {
            synchronized (TestCasts.class) {
                // synchronized to make sure isCompiled is re-read
                if (root.isCompiled) {
                    break;
                }
            }
            target.call(values[i]);
            i++;
            if (i == values.length) {
                i = 0;
            }
        }
        assert root.isCompiled;

        for (Object deOptVal : deOptVals) {
            target.call(deOptVal);
            assert !root.isCompiled;
        }
    }

    @Test
    public void testFirstIntegers() {
        class Root extends TestRootNode<CastNode> {

            protected Root(String name) {
                super(name, new CastBuilder().firstIntegerWithError(0, Message.INVALID_ARGUMENT, "foo").getCasts()[0]);
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                // use "new Integer(...)" to avoid boxing logic
                return new Integer((int) node.execute(value));
            }
        }
        testCompilation(new Object[]{1, 2, 3}, new Root("FirstInteger"));
        testCompilation(new Object[]{1, 2, RDataFactory.createIntVectorFromScalar(55)}, new Root("FirstIntegerWithVectors"));
        testCompilation(new Object[]{1.2, 2, (byte) 1}, new Root("FirstIntegerWithCoerce"));
    }

    @Test
    public void testFirstIntegerWithConstant() {
        class Root extends TestRootNode<CastNode> {

            private final Object constant;

            protected Root(String name, Object constant) {
                super(name, new CastBuilder().firstIntegerWithError(0, Message.INVALID_ARGUMENT, "foo").getCasts()[0]);
                this.constant = constant;
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                int result = (int) node.execute(constant);
                CompilerAsserts.compilationConstant(result);
                return null;
            }
        }
        testCompilation(new Object[]{1}, new Root("FirstIntegerWithConstant", 1));
        testCompilation(new Object[]{1}, new Root("FirstIntegerWithConstant", 44.5));
        testCompilation(new Object[]{1}, new Root("FirstIntegerWithConstant", (byte) 1));
    }

    @Test
    public void testMustBe() {
        class Root extends TestRootNode<CastNode> {

            protected Root(String name) {
                super(name, new CastBuilder().arg(0).
                                mustBe(integerValue()).
                                builder().getCasts()[0]);
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                Object res = node.execute(value);
                return res;
            }
        }
        testCompilation(new Object[]{1, 2, 3}, new Root("MustBeInteger"));
        testCompilation(new Object[]{1, 2, RDataFactory.createIntVectorFromScalar(55)}, new Root("MustBeIntegerWithVectors"));
    }

    @Test
    public void testMapDefaultValue() {
        class Root extends TestRootNode<CastNode> {

            protected Root(String name) {
                super(name, new CastBuilder().arg(0).
                                map(defaultValue("X")).
                                builder().getCasts()[0]);
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                Object res = node.execute(value);
                return res;
            }
        }
        testCompilation(new Object[]{RNull.instance}, new Root("MapDefaultValueNull"));
        testCompilation(new Object[]{1}, new Root("MapDefaultValueNonNull"));
    }

    @Test
    public void testMapCharAt0() {
        class Root extends TestRootNode<CastNode> {

            protected Root(String name) {
                super(name, new CastBuilder().arg(0).
                                mustBe(String.class).
                                map(charAt0(0)).
                                builder().getCasts()[0]);
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                Object res = node.execute(value);
                return res;
            }
        }
        testCompilation(new Object[]{"abc"}, new Root("MapCharAt0NonEmptyString"));
        testCompilation(new Object[]{""}, new Root("MapCharAt0EmptyString"));
    }

    @Test
    public void testMapConstant() {
        class Root extends TestRootNode<CastNode> {

            final boolean mustBeResultCompilationConstant;

            protected Root(String name, boolean mustBeResultCompilationConstant) {
                super(name, new CastBuilder().arg(0).
                                mapIf(scalarIntegerValue, constant(10)).
                                builder().getCasts()[0]);
                this.mustBeResultCompilationConstant = mustBeResultCompilationConstant;
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                Object res = node.execute(value);
                if (mustBeResultCompilationConstant) {
                    CompilerAsserts.compilationConstant(res);
                }
                return res;
            }
        }
        testCompilation(new Object[]{1, 2, 3}, new Root("MapConstantInt", true));
        testCompilation(new Object[]{"abc"}, new Root("MapConstantNoInt", false));
    }

    @Test
    public void testMustBeWithConstant() {
        class Root extends TestRootNode<CastNode> {

            private final Object constant;

            protected Root(String name, Object constant) {
                super(name, new CastBuilder().arg(0).
                                mustBe(integerValue()).
                                builder().getCasts()[0]);
                this.constant = constant;
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                int result = (int) node.execute(constant);
                CompilerAsserts.compilationConstant(result);
                return null;
            }
        }
        testCompilation(new Object[]{1}, new Root("MustBeWithConstant", 1));
    }

    @Test
    public void testConditionalMapChainWithConstant() {
        class Root extends TestRootNode<CastNode> {

            private final Object constant;

            protected Root(String name, Object constant) {
                super(name, new CastBuilder().arg(0).
                                mapIf(stringValue(), asStringVector()).
                                mapIf(integerValue(), asIntegerVector()).
                                mapIf(logicalValue(), asLogicalVector(), asDoubleVector()).
                                builder().getCasts()[0]);
                this.constant = constant;
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                // return node.execute(value);
                Object result = node.execute(constant);
                CompilerAsserts.compilationConstant(result);
                return null;
            }
        }
        testCompilation(new Object[]{1}, new Root("ConditionalMapChainWithIntegerConstant", 1));
        testCompilation(new Object[]{1}, new Root("ConditionalMapChainWithStringConstant", "aaa"));
        testCompilation(new Object[]{1}, new Root("ConditionalMapChainWithLogicalConstant", RRuntime.LOGICAL_TRUE));
        testCompilation(new Object[]{1}, new Root("ConditionalMapChainWithDoubleConstant1", 1.2));
        testCompilation(new Object[]{1}, new Root("ConditionalMapChainWithDoubleConstant2", Math.PI));
    }

    @Test
    public void testConditionalMapChain() {
        class Root extends TestRootNode<CastNode> {

            protected Root(String name) {
                super(name, new CastBuilder().arg(0).
                                mapIf(stringValue(), asStringVector()).
                                mapIf(doubleValue(), asDoubleVector()).
                                mapIf(logicalValue(), asLogicalVector(), asIntegerVector()).
                                builder().getCasts()[0]);
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                Object res = node.execute(value);
                return null;
            }
        }
        testCompilation(new Object[]{1, 2, 3}, new Root("ConditionalMapChainFedByInteger1"), 1.1, "abc", RRuntime.LOGICAL_FALSE);
        testCompilation(new Object[]{1, RDataFactory.createIntVector(new int[]{55, 66}, true),
                        RDataFactory.createIntVectorFromScalar(77)}, new Root(
                        "ConditionalMapChainFedByInteger2"), 1.1, "abc", RRuntime.LOGICAL_FALSE);
        testCompilation(new Object[]{1.1, 2.2, 3.3}, new Root("ConditionalMapChainFedByDouble1"), 1, "abc", RRuntime.LOGICAL_FALSE);
        testCompilation(new Object[]{1.1, RDataFactory.createDoubleVector(new double[]{55.55, 66.66},
                        true), RDataFactory.createDoubleVectorFromScalar(77.77)}, new Root(
                        "ConditionalMapChainFedByDouble2"), 1, "abc", RRuntime.LOGICAL_FALSE);
        testCompilation(new Object[]{RRuntime.LOGICAL_TRUE, RRuntime.LOGICAL_FALSE,
                        RRuntime.LOGICAL_TRUE}, new Root("ConditionalMapChainFedByLogical1"), 1, "abc", 1.1);
        testCompilation(new Object[]{RRuntime.LOGICAL_FALSE, RDataFactory.createLogicalVector(new
                        byte[]{RRuntime.LOGICAL_FALSE, RRuntime.LOGICAL_TRUE}, true),
                        RDataFactory.createLogicalVectorFromScalar(RRuntime.LOGICAL_FALSE)}, new
                        Root("ConditionalMapChainFedByLogical2"), 1, "abc", 1.1);
        testCompilation(new Object[]{"", "abc", "xyz"}, new Root("ConditionalMapChainFedByString1"), 1.1, 1, RRuntime.LOGICAL_FALSE);
        testCompilation(new Object[]{"abc", RDataFactory.createStringVector(new String[]{"", "xyz"},
                        true),
                        RDataFactory.createStringVectorFromScalar("abc")}, new Root("ConditionalMapChainFedByString2"), 1.1, 1, RRuntime.LOGICAL_FALSE);
    }

    @Test
    public void testComplexPipeline1() {
        class Root extends TestRootNode<CastNode> {

            protected Root(String name) {
                super(name, new CastBuilder().output(NullStream.INSTANCE).arg(0).
                                mustBe(numericValue).
                                asVector().
                                mustBe(singleElement()).
                                findFirst().
                                mustBe(nullValue().not()).
                                shouldBe(ValuePredicateArgumentFilter.fromLambda(x -> x instanceof Byte || x instanceof Integer && ((Integer) x) > 0, Object.class), Message.NON_POSITIVE_FILL).
                                mapIf(scalarLogicalValue, asBoolean(), asInteger()).
                                builder().getCasts()[0]);
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                Object res = node.execute(value);
                return null;
            }
        }
        testCompilation(new Object[]{RDataFactory.createIntVectorFromScalar(77)}, new Root("ComplexPipeline1Integer"));
        testCompilation(new Object[]{RDataFactory.createIntVectorFromScalar(77), RDataFactory.createLogicalVectorFromScalar(RRuntime.LOGICAL_FALSE)}, new Root("ComplexPipeline1IntegerLogical"));
        testCompilation(new Object[]{RDataFactory.createIntVectorFromScalar(77), RDataFactory.createDoubleVectorFromScalar(77.77)}, new Root("ComplexPipeline1IntegerDouble"));
    }

    @Test
    public void testComplexPipeline2() {
        class Root extends TestRootNode<CastNode> {

            protected Root(String name) {
                super(name, new CastBuilder().output(NullStream.INSTANCE).arg(0).
                                mustBe(stringValue()).
                                asStringVector().
                                mustBe(singleElement()).
                                findFirst().
                                mustBe(lengthLte(1)).
                                map(charAt0(RRuntime.INT_NA)).
                                notNA(100000).
                                builder().getCasts()[0]);
            }

            @Override
            protected Object execute(VirtualFrame frame, Object value) {
                Object res = node.execute(value);
                return null;
            }
        }
        testCompilation(new Object[]{RDataFactory.createStringVectorFromScalar("")}, new Root("ComplexPipeline2EmptyString"));
        testCompilation(new Object[]{RDataFactory.createStringVectorFromScalar("a")}, new Root("ComplexPipeline2OneCharString"));
    }

    static class NullStream extends OutputStream {

        static final NullStream INSTANCE = new NullStream();

        @Override
        public void write(int b) throws IOException {
        }

    }

}
