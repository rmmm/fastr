// GENERATED by com.oracle.truffle.r.ffi.codegen.FFIUpCallsIndexCodeGen class; DO NOT EDIT
// This file can be regenerated by running 'mx rfficodegen'
#ifndef RFFI_UPCALLSINDEX_H
#define RFFI_UPCALLSINDEX_H

#define ATTRIB_x 0
#define BODY_x 1
#define CAAR_x 2
#define CAD4R_x 3
#define CADDDR_x 4
#define CADDR_x 5
#define CADR_x 6
#define CAR_x 7
#define CDAR_x 8
#define CDDDR_x 9
#define CDDR_x 10
#define CDR_x 11
#define CLOENV_x 12
#define COMPLEX_x 13
#define DUPLICATE_ATTRIB_x 14
#define ENCLOS_x 15
#define FASTR_DATAPTR_x 16
#define FASTR_getConnectionChar_x 17
#define FORMALS_x 18
#define GetRNGstate_x 19
#define INTEGER_x 20
#define IS_S4_OBJECT_x 21
#define LENGTH_x 22
#define LEVELS_x 23
#define LOGICAL_x 24
#define NAMED_x 25
#define OBJECT_x 26
#define PRCODE_x 27
#define PRENV_x 28
#define PRINTNAME_x 29
#define PRSEEN_x 30
#define PRVALUE_x 31
#define PutRNGstate_x 32
#define RAW_x 33
#define RDEBUG_x 34
#define REAL_x 35
#define RSTEP_x 36
#define R_BaseEnv_x 37
#define R_BaseNamespace_x 38
#define R_BindingIsLocked_x 39
#define R_CHAR_x 40
#define R_CleanUp_x 41
#define R_ExternalPtrAddr_x 42
#define R_ExternalPtrProtected_x 43
#define R_ExternalPtrTag_x 44
#define R_FindNamespace_x 45
#define R_GetConnection_x 46
#define R_GlobalContext_x 47
#define R_GlobalEnv_x 48
#define R_Home_x 49
#define R_HomeDir_x 50
#define R_Interactive_x 51
#define R_LockBinding_x 52
#define R_MakeExternalPtr_x 53
#define R_MethodsNamespace_x 54
#define R_NamespaceRegistry_x 55
#define R_NewHashedEnv_x 56
#define R_ParseVector_x 57
#define R_PreserveObject_x 58
#define R_PromiseExpr_x 59
#define R_ProtectWithIndex_x 60
#define R_ReadConnection_x 61
#define R_ReleaseObject_x 62
#define R_Reprotect_x 63
#define R_SetExternalPtrAddr_x 64
#define R_SetExternalPtrProtected_x 65
#define R_SetExternalPtrTag_x 66
#define R_TempDir_x 67
#define R_ToplevelExec_x 68
#define R_WriteConnection_x 69
#define R_alloc_x 70
#define R_compute_identical_x 71
#define R_do_MAKE_CLASS_x 72
#define R_do_new_object_x 73
#define R_do_slot_x 74
#define R_do_slot_assign_x 75
#define R_forceAndCall_x 76
#define R_getClassDef_x 77
#define R_getContextCall_x 78
#define R_getContextEnv_x 79
#define R_getContextFun_x 80
#define R_getContextSrcRef_x 81
#define R_getGlobalFunctionContext_x 82
#define R_getParentFunctionContext_x 83
#define R_has_slot_x 84
#define R_insideBrowser_x 85
#define R_isEqual_x 86
#define R_isGlobal_x 87
#define R_lsInternal3_x 88
#define R_nchar_x 89
#define R_new_custom_connection_x 90
#define R_tryEval_x 91
#define R_unLockBinding_x 92
#define Rf_GetOption1_x 93
#define Rf_NonNullStringMatch_x 94
#define Rf_PairToVectorList_x 95
#define Rf_PrintValue_x 96
#define Rf_ScalarInteger_x 97
#define Rf_ScalarLogical_x 98
#define Rf_ScalarReal_x 99
#define Rf_ScalarString_x 100
#define Rf_VectorToPairList_x 101
#define Rf_allocArray_x 102
#define Rf_allocMatrix_x 103
#define Rf_allocVector_x 104
#define Rf_any_duplicated_x 105
#define Rf_any_duplicated3_x 106
#define Rf_asChar_x 107
#define Rf_asCharacterFactor_x 108
#define Rf_asInteger_x 109
#define Rf_asLogical_x 110
#define Rf_asReal_x 111
#define Rf_bessel_i_x 112
#define Rf_bessel_i_ex_x 113
#define Rf_bessel_j_x 114
#define Rf_bessel_j_ex_x 115
#define Rf_bessel_k_x 116
#define Rf_bessel_k_ex_x 117
#define Rf_bessel_y_x 118
#define Rf_bessel_y_ex_x 119
#define Rf_beta_x 120
#define Rf_choose_x 121
#define Rf_classgets_x 122
#define Rf_coerceVector_x 123
#define Rf_cons_x 124
#define Rf_copyListMatrix_x 125
#define Rf_copyMatrix_x 126
#define Rf_copyMostAttrib_x 127
#define Rf_cospi_x 128
#define Rf_dbeta_x 129
#define Rf_dbinom_x 130
#define Rf_dcauchy_x 131
#define Rf_dchisq_x 132
#define Rf_defineVar_x 133
#define Rf_dexp_x 134
#define Rf_df_x 135
#define Rf_dgamma_x 136
#define Rf_dgeom_x 137
#define Rf_dhyper_x 138
#define Rf_digamma_x 139
#define Rf_dlnorm_x 140
#define Rf_dlogis_x 141
#define Rf_dnbeta_x 142
#define Rf_dnbinom_x 143
#define Rf_dnbinom_mu_x 144
#define Rf_dnchisq_x 145
#define Rf_dnf_x 146
#define Rf_dnorm4_x 147
#define Rf_dnt_x 148
#define Rf_dpois_x 149
#define Rf_dpsifn_x 150
#define Rf_dsignrank_x 151
#define Rf_dt_x 152
#define Rf_dunif_x 153
#define Rf_duplicate_x 154
#define Rf_dweibull_x 155
#define Rf_dwilcox_x 156
#define Rf_error_x 157
#define Rf_errorcall_x 158
#define Rf_eval_x 159
#define Rf_findFun_x 160
#define Rf_findVar_x 161
#define Rf_findVarInFrame_x 162
#define Rf_findVarInFrame3_x 163
#define Rf_fprec_x 164
#define Rf_ftrunc_x 165
#define Rf_gammafn_x 166
#define Rf_getAttrib_x 167
#define Rf_gsetVar_x 168
#define Rf_inherits_x 169
#define Rf_install_x 170
#define Rf_installChar_x 171
#define Rf_isNull_x 172
#define Rf_isObject_x 173
#define Rf_isString_x 174
#define Rf_lbeta_x 175
#define Rf_lchoose_x 176
#define Rf_lengthgets_x 177
#define Rf_lgamma1p_x 178
#define Rf_lgammafn_x 179
#define Rf_lgammafn_sign_x 180
#define Rf_log1pexp_x 181
#define Rf_log1pmx_x 182
#define Rf_logspace_add_x 183
#define Rf_logspace_sub_x 184
#define Rf_match_x 185
#define Rf_mkCharLenCE_x 186
#define Rf_namesgets_x 187
#define Rf_ncols_x 188
#define Rf_nrows_x 189
#define Rf_pbeta_x 190
#define Rf_pbinom_x 191
#define Rf_pcauchy_x 192
#define Rf_pchisq_x 193
#define Rf_pentagamma_x 194
#define Rf_pexp_x 195
#define Rf_pf_x 196
#define Rf_pgamma_x 197
#define Rf_pgeom_x 198
#define Rf_phyper_x 199
#define Rf_plnorm_x 200
#define Rf_plogis_x 201
#define Rf_pnbeta_x 202
#define Rf_pnbinom_x 203
#define Rf_pnbinom_mu_x 204
#define Rf_pnchisq_x 205
#define Rf_pnf_x 206
#define Rf_pnorm5_x 207
#define Rf_pnorm_both_x 208
#define Rf_pnt_x 209
#define Rf_ppois_x 210
#define Rf_protect_x 211
#define Rf_psigamma_x 212
#define Rf_psignrank_x 213
#define Rf_pt_x 214
#define Rf_ptukey_x 215
#define Rf_punif_x 216
#define Rf_pweibull_x 217
#define Rf_pwilcox_x 218
#define Rf_qbeta_x 219
#define Rf_qbinom_x 220
#define Rf_qcauchy_x 221
#define Rf_qchisq_x 222
#define Rf_qexp_x 223
#define Rf_qf_x 224
#define Rf_qgamma_x 225
#define Rf_qgeom_x 226
#define Rf_qhyper_x 227
#define Rf_qlnorm_x 228
#define Rf_qlogis_x 229
#define Rf_qnbeta_x 230
#define Rf_qnbinom_x 231
#define Rf_qnbinom_mu_x 232
#define Rf_qnchisq_x 233
#define Rf_qnf_x 234
#define Rf_qnorm5_x 235
#define Rf_qnt_x 236
#define Rf_qpois_x 237
#define Rf_qsignrank_x 238
#define Rf_qt_x 239
#define Rf_qtukey_x 240
#define Rf_qunif_x 241
#define Rf_qweibull_x 242
#define Rf_qwilcox_x 243
#define Rf_rbeta_x 244
#define Rf_rbinom_x 245
#define Rf_rcauchy_x 246
#define Rf_rchisq_x 247
#define Rf_rexp_x 248
#define Rf_rf_x 249
#define Rf_rgamma_x 250
#define Rf_rgeom_x 251
#define Rf_rhyper_x 252
#define Rf_rlnorm_x 253
#define Rf_rlogis_x 254
#define Rf_rmultinom_x 255
#define Rf_rnbinom_x 256
#define Rf_rnbinom_mu_x 257
#define Rf_rnchisq_x 258
#define Rf_rnorm_x 259
#define Rf_rpois_x 260
#define Rf_rsignrank_x 261
#define Rf_rt_x 262
#define Rf_runif_x 263
#define Rf_rweibull_x 264
#define Rf_rwilcox_x 265
#define Rf_setAttrib_x 266
#define Rf_sign_x 267
#define Rf_sinpi_x 268
#define Rf_str2type_x 269
#define Rf_tanpi_x 270
#define Rf_tetragamma_x 271
#define Rf_trigamma_x 272
#define Rf_unprotect_x 273
#define Rf_unprotect_ptr_x 274
#define Rf_warning_x 275
#define Rf_warningcall_x 276
#define Rprintf_x 277
#define SETCAD4R_x 278
#define SETCADDDR_x 279
#define SETCADDR_x 280
#define SETCADR_x 281
#define SETCAR_x 282
#define SETCDR_x 283
#define SETLENGTH_x 284
#define SETLEVELS_x 285
#define SET_ATTRIB_x 286
#define SET_BODY_x 287
#define SET_CLOENV_x 288
#define SET_ENCLOS_x 289
#define SET_FORMALS_x 290
#define SET_NAMED_FASTR_x 291
#define SET_OBJECT_x 292
#define SET_RDEBUG_x 293
#define SET_RSTEP_x 294
#define SET_S4_OBJECT_x 295
#define SET_STRING_ELT_x 296
#define SET_SYMVALUE_x 297
#define SET_TAG_x 298
#define SET_TRUELENGTH_x 299
#define SET_TYPEOF_x 300
#define SET_VECTOR_ELT_x 301
#define STRING_ELT_x 302
#define SYMVALUE_x 303
#define TAG_x 304
#define TRUELENGTH_x 305
#define TYPEOF_x 306
#define UNSET_S4_OBJECT_x 307
#define VECTOR_ELT_x 308
#define forceSymbols_x 309
#define getCCallable_x 310
#define getConnectionClassString_x 311
#define getEmbeddingDLLInfo_x 312
#define getOpenModeString_x 313
#define getSummaryDescription_x 314
#define isSeekable_x 315
#define octsize_x 316
#define registerCCallable_x 317
#define registerRoutines_x 318
#define restoreHandlerStacks_x 319
#define setDotSymbolValues_x 320
#define unif_rand_x 321
#define useDynamicSymbols_x 322

#define UPCALLS_TABLE_SIZE 323

#endif // RFFI_UPCALLSINDEX_H
