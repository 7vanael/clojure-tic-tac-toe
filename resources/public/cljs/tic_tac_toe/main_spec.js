// Compiled by ClojureScript 1.12.42 {:optimizations :none}
goog.provide('tic_tac_toe.main_spec');
goog.require('cljs.core');
goog.require('tic_tac_toe.main');
var description__7791__auto___8260 = speclj.components.new_description("main",false,"tic-tac-toe.main-spec");
var _STAR_parent_description_STAR__orig_val__8254_8261 = speclj.config._STAR_parent_description_STAR_;
var _STAR_parent_description_STAR__temp_val__8255_8262 = description__7791__auto___8260;
(speclj.config._STAR_parent_description_STAR_ = _STAR_parent_description_STAR__temp_val__8255_8262);

try{var seq__8256_8263 = cljs.core.seq.call(null,(new cljs.core.PersistentVector(null,1,(5),cljs.core.PersistentVector.EMPTY_NODE,[speclj.components.new_characteristic("hello is goodbye",((function (_STAR_parent_description_STAR__orig_val__8254_8261,_STAR_parent_description_STAR__temp_val__8255_8262,description__7791__auto___8260){
return (function (){
var expected__7924__auto__ = (-1);
var actual__7925__auto__ = tic_tac_toe.main.hello.call(null);
if(cljs.core._EQ_.call(null,expected__7924__auto__,actual__7925__auto__)){
return null;
} else {
throw (new speclj.platform.SpecFailure(["Expected: ",(((expected__7924__auto__ == null))?"nil":cljs.core.pr_str.call(null,expected__7924__auto__)),cljs.core.str.cljs$core$IFn$_invoke$arity$1(speclj.platform.endl),"     got: ",(((actual__7925__auto__ == null))?"nil":cljs.core.pr_str.call(null,actual__7925__auto__))," (using =)"].join('')));
}
});})(_STAR_parent_description_STAR__orig_val__8254_8261,_STAR_parent_description_STAR__temp_val__8255_8262,description__7791__auto___8260))
,false)],null)));
var chunk__8257_8264 = null;
var count__8258_8265 = (0);
var i__8259_8266 = (0);
while(true){
if((i__8259_8266 < count__8258_8265)){
var component__7792__auto___8267 = cljs.core._nth.call(null,chunk__8257_8264,i__8259_8266);
speclj.components.install(component__7792__auto___8267,description__7791__auto___8260);


var G__8268 = seq__8256_8263;
var G__8269 = chunk__8257_8264;
var G__8270 = count__8258_8265;
var G__8271 = (i__8259_8266 + (1));
seq__8256_8263 = G__8268;
chunk__8257_8264 = G__8269;
count__8258_8265 = G__8270;
i__8259_8266 = G__8271;
continue;
} else {
var temp__5825__auto___8272 = cljs.core.seq.call(null,seq__8256_8263);
if(temp__5825__auto___8272){
var seq__8256_8273__$1 = temp__5825__auto___8272;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__8256_8273__$1)){
var c__5548__auto___8274 = cljs.core.chunk_first.call(null,seq__8256_8273__$1);
var G__8275 = cljs.core.chunk_rest.call(null,seq__8256_8273__$1);
var G__8276 = c__5548__auto___8274;
var G__8277 = cljs.core.count.call(null,c__5548__auto___8274);
var G__8278 = (0);
seq__8256_8263 = G__8275;
chunk__8257_8264 = G__8276;
count__8258_8265 = G__8277;
i__8259_8266 = G__8278;
continue;
} else {
var component__7792__auto___8279 = cljs.core.first.call(null,seq__8256_8273__$1);
speclj.components.install(component__7792__auto___8279,description__7791__auto___8260);


var G__8280 = cljs.core.next.call(null,seq__8256_8273__$1);
var G__8281 = null;
var G__8282 = (0);
var G__8283 = (0);
seq__8256_8263 = G__8280;
chunk__8257_8264 = G__8281;
count__8258_8265 = G__8282;
i__8259_8266 = G__8283;
continue;
}
} else {
}
}
break;
}
}finally {(speclj.config._STAR_parent_description_STAR_ = _STAR_parent_description_STAR__orig_val__8254_8261);
}
if(cljs.core.truth_(speclj.config._STAR_parent_description_STAR_)){
} else {
speclj.running.submit_description(speclj.config.active_runner(),description__7791__auto___8260);
}


//# sourceMappingURL=main_spec.js.map
