// Compiled by ClojureScript 1.12.42 {:optimizations :none}
goog.provide('speclj.running');
goog.require('cljs.core');
goog.require('clojure.string');
goog.require('speclj.components');
goog.require('speclj.config');
goog.require('speclj.platform');
goog.require('speclj.reporting');
goog.require('speclj.results');
goog.require('speclj.tags');
speclj.running.focusable_QMARK_ = (function speclj$running$focusable_QMARK_(component){
return (((!((component == null)))) && (((speclj.components.is_description_QMARK_.call(null,component)) || (speclj.components.is_characteristic_QMARK_.call(null,component)))));
});
speclj.running.focused_QMARK_ = (function speclj$running$focused_QMARK_(component){
return cljs.core.deref.call(null,component.is_focused_QMARK_);
});
speclj.running.has_focus_QMARK_ = (function speclj$running$has_focus_QMARK_(component){
var and__5023__auto__ = speclj.components.is_description_QMARK_.call(null,component);
if(and__5023__auto__){
return cljs.core.deref.call(null,component.has_focus_QMARK_);
} else {
return and__5023__auto__;
}
});
speclj.running.focus_mode_QMARK_ = (function speclj$running$focus_mode_QMARK_(component){
while(true){
var or__5025__auto__ = speclj.running.focused_QMARK_.call(null,component);
if(cljs.core.truth_(or__5025__auto__)){
return or__5025__auto__;
} else {
var or__5025__auto____$1 = speclj.running.has_focus_QMARK_.call(null,component);
if(cljs.core.truth_(or__5025__auto____$1)){
return or__5025__auto____$1;
} else {
var temp__5825__auto__ = cljs.core.deref.call(null,component.parent);
if(cljs.core.truth_(temp__5825__auto__)){
var parent = temp__5825__auto__;
var G__7252 = parent;
component = G__7252;
continue;
} else {
return null;
}
}
}
break;
}
});
speclj.running.can_run_QMARK_ = (function speclj$running$can_run_QMARK_(component){
var or__5025__auto__ = speclj.running.focused_QMARK_.call(null,component);
if(cljs.core.truth_(or__5025__auto__)){
return or__5025__auto__;
} else {
var or__5025__auto____$1 = speclj.running.has_focus_QMARK_.call(null,component);
if(cljs.core.truth_(or__5025__auto____$1)){
return or__5025__auto____$1;
} else {
return cljs.core.not.call(null,speclj.running.focus_mode_QMARK_.call(null,component));
}
}
});
speclj.running.all_children = (function speclj$running$all_children(component){
if(speclj.components.is_description_QMARK_.call(null,component)){
return cljs.core.concat.call(null,cljs.core.deref.call(null,component.characteristics),cljs.core.deref.call(null,component.children));
} else {
return cljs.core.PersistentVector.EMPTY;
}
});
speclj.running.focus_BANG_ = (function speclj$running$focus_BANG_(component){
return cljs.core.reset_BANG_.call(null,component.is_focused_QMARK_,true);
});
speclj.running.focus_characteristics_BANG_ = (function speclj$running$focus_characteristics_BANG_(component){
speclj.running.focus_BANG_.call(null,component);

return cljs.core.doall.call(null,cljs.core.map.call(null,speclj.running.focus_BANG_,cljs.core.deref.call(null,component.characteristics)));
});
speclj.running.focus_children_BANG_ = (function speclj$running$focus_children_BANG_(component){
speclj.running.focus_BANG_.call(null,component);

return cljs.core.doall.call(null,cljs.core.map.call(null,speclj.running.focus_children_BANG_,cljs.core.deref.call(null,component.children)));
});
speclj.running.enable_focus_mode_BANG_ = (function speclj$running$enable_focus_mode_BANG_(component){
while(true){
var temp__5825__auto__ = cljs.core.deref.call(null,component.parent);
if(cljs.core.truth_(temp__5825__auto__)){
var parent = temp__5825__auto__;
cljs.core.reset_BANG_.call(null,parent.has_focus_QMARK_,true);

var G__7267 = parent;
component = G__7267;
continue;
} else {
return null;
}
break;
}
});
speclj.running.track_focused_descriptions_BANG_ = (function speclj$running$track_focused_descriptions_BANG_(descriptions){
var seq__7263 = cljs.core.seq.call(null,descriptions);
var chunk__7264 = null;
var count__7265 = (0);
var i__7266 = (0);
while(true){
if((i__7266 < count__7265)){
var component = cljs.core._nth.call(null,chunk__7264,i__7266);
if(cljs.core.truth_(speclj.running.focused_QMARK_.call(null,component))){
speclj.running.enable_focus_mode_BANG_.call(null,component);

speclj.running.focus_children_BANG_.call(null,component);

speclj.running.focus_characteristics_BANG_.call(null,component);
} else {
}


var G__7313 = seq__7263;
var G__7314 = chunk__7264;
var G__7315 = count__7265;
var G__7316 = (i__7266 + (1));
seq__7263 = G__7313;
chunk__7264 = G__7314;
count__7265 = G__7315;
i__7266 = G__7316;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7263);
if(temp__5825__auto__){
var seq__7263__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7263__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7263__$1);
var G__7321 = cljs.core.chunk_rest.call(null,seq__7263__$1);
var G__7322 = c__5548__auto__;
var G__7323 = cljs.core.count.call(null,c__5548__auto__);
var G__7324 = (0);
seq__7263 = G__7321;
chunk__7264 = G__7322;
count__7265 = G__7323;
i__7266 = G__7324;
continue;
} else {
var component = cljs.core.first.call(null,seq__7263__$1);
if(cljs.core.truth_(speclj.running.focused_QMARK_.call(null,component))){
speclj.running.enable_focus_mode_BANG_.call(null,component);

speclj.running.focus_children_BANG_.call(null,component);

speclj.running.focus_characteristics_BANG_.call(null,component);
} else {
}


var G__7332 = cljs.core.next.call(null,seq__7263__$1);
var G__7333 = null;
var G__7334 = (0);
var G__7335 = (0);
seq__7263 = G__7332;
chunk__7264 = G__7333;
count__7265 = G__7334;
i__7266 = G__7335;
continue;
}
} else {
return null;
}
}
break;
}
});
speclj.running.track_focused_characteristics_BANG_ = (function speclj$running$track_focused_characteristics_BANG_(characteristics){
var seq__7297 = cljs.core.seq.call(null,characteristics);
var chunk__7299 = null;
var count__7300 = (0);
var i__7301 = (0);
while(true){
if((i__7301 < count__7300)){
var characteristic = cljs.core._nth.call(null,chunk__7299,i__7301);
if(cljs.core.truth_(speclj.running.focused_QMARK_.call(null,characteristic))){
speclj.running.enable_focus_mode_BANG_.call(null,characteristic);


var G__7349 = seq__7297;
var G__7350 = chunk__7299;
var G__7351 = count__7300;
var G__7352 = (i__7301 + (1));
seq__7297 = G__7349;
chunk__7299 = G__7350;
count__7300 = G__7351;
i__7301 = G__7352;
continue;
} else {
var G__7353 = seq__7297;
var G__7354 = chunk__7299;
var G__7355 = count__7300;
var G__7356 = (i__7301 + (1));
seq__7297 = G__7353;
chunk__7299 = G__7354;
count__7300 = G__7355;
i__7301 = G__7356;
continue;
}
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7297);
if(temp__5825__auto__){
var seq__7297__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7297__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7297__$1);
var G__7359 = cljs.core.chunk_rest.call(null,seq__7297__$1);
var G__7360 = c__5548__auto__;
var G__7361 = cljs.core.count.call(null,c__5548__auto__);
var G__7362 = (0);
seq__7297 = G__7359;
chunk__7299 = G__7360;
count__7300 = G__7361;
i__7301 = G__7362;
continue;
} else {
var characteristic = cljs.core.first.call(null,seq__7297__$1);
if(cljs.core.truth_(speclj.running.focused_QMARK_.call(null,characteristic))){
speclj.running.enable_focus_mode_BANG_.call(null,characteristic);


var G__7363 = cljs.core.next.call(null,seq__7297__$1);
var G__7364 = null;
var G__7365 = (0);
var G__7366 = (0);
seq__7297 = G__7363;
chunk__7299 = G__7364;
count__7300 = G__7365;
i__7301 = G__7366;
continue;
} else {
var G__7367 = cljs.core.next.call(null,seq__7297__$1);
var G__7368 = null;
var G__7369 = (0);
var G__7370 = (0);
seq__7297 = G__7367;
chunk__7299 = G__7368;
count__7300 = G__7369;
i__7301 = G__7370;
continue;
}
}
} else {
return null;
}
}
break;
}
});
speclj.running.scan_for_focus_BANG_ = (function speclj$running$scan_for_focus_BANG_(description){
var all = cljs.core.tree_seq.call(null,cljs.core.some_QMARK_,speclj.running.all_children,description);
speclj.running.track_focused_descriptions_BANG_.call(null,cljs.core.filter.call(null,speclj.components.is_description_QMARK_,all));

speclj.running.track_focused_characteristics_BANG_.call(null,cljs.core.filter.call(null,speclj.components.is_characteristic_QMARK_,all));

return description;
});
speclj.running.filter_focused = (function speclj$running$filter_focused(descriptions){
var seq__7345_7375 = cljs.core.seq.call(null,descriptions);
var chunk__7346_7376 = null;
var count__7347_7377 = (0);
var i__7348_7378 = (0);
while(true){
if((i__7348_7378 < count__7347_7377)){
var description_7379 = cljs.core._nth.call(null,chunk__7346_7376,i__7348_7378);
speclj.running.scan_for_focus_BANG_.call(null,description_7379);


var G__7380 = seq__7345_7375;
var G__7381 = chunk__7346_7376;
var G__7382 = count__7347_7377;
var G__7383 = (i__7348_7378 + (1));
seq__7345_7375 = G__7380;
chunk__7346_7376 = G__7381;
count__7347_7377 = G__7382;
i__7348_7378 = G__7383;
continue;
} else {
var temp__5825__auto___7384 = cljs.core.seq.call(null,seq__7345_7375);
if(temp__5825__auto___7384){
var seq__7345_7385__$1 = temp__5825__auto___7384;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7345_7385__$1)){
var c__5548__auto___7386 = cljs.core.chunk_first.call(null,seq__7345_7385__$1);
var G__7387 = cljs.core.chunk_rest.call(null,seq__7345_7385__$1);
var G__7388 = c__5548__auto___7386;
var G__7389 = cljs.core.count.call(null,c__5548__auto___7386);
var G__7390 = (0);
seq__7345_7375 = G__7387;
chunk__7346_7376 = G__7388;
count__7347_7377 = G__7389;
i__7348_7378 = G__7390;
continue;
} else {
var description_7391 = cljs.core.first.call(null,seq__7345_7385__$1);
speclj.running.scan_for_focus_BANG_.call(null,description_7391);


var G__7392 = cljs.core.next.call(null,seq__7345_7385__$1);
var G__7393 = null;
var G__7394 = (0);
var G__7395 = (0);
seq__7345_7375 = G__7392;
chunk__7346_7376 = G__7393;
count__7347_7377 = G__7394;
i__7348_7378 = G__7395;
continue;
}
} else {
}
}
break;
}

var or__5025__auto__ = cljs.core.seq.call(null,cljs.core.filter.call(null,speclj.running.focus_mode_QMARK_,descriptions));
if(or__5025__auto__){
return or__5025__auto__;
} else {
return descriptions;
}
});
speclj.running.eval_components = (function speclj$running$eval_components(components){
var seq__7371 = cljs.core.seq.call(null,components);
var chunk__7372 = null;
var count__7373 = (0);
var i__7374 = (0);
while(true){
if((i__7374 < count__7373)){
var component = cljs.core._nth.call(null,chunk__7372,i__7374);
component.body.call(null);


var G__7400 = seq__7371;
var G__7401 = chunk__7372;
var G__7402 = count__7373;
var G__7403 = (i__7374 + (1));
seq__7371 = G__7400;
chunk__7372 = G__7401;
count__7373 = G__7402;
i__7374 = G__7403;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7371);
if(temp__5825__auto__){
var seq__7371__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7371__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7371__$1);
var G__7408 = cljs.core.chunk_rest.call(null,seq__7371__$1);
var G__7409 = c__5548__auto__;
var G__7410 = cljs.core.count.call(null,c__5548__auto__);
var G__7411 = (0);
seq__7371 = G__7408;
chunk__7372 = G__7409;
count__7373 = G__7410;
i__7374 = G__7411;
continue;
} else {
var component = cljs.core.first.call(null,seq__7371__$1);
component.body.call(null);


var G__7414 = cljs.core.next.call(null,seq__7371__$1);
var G__7415 = null;
var G__7416 = (0);
var G__7417 = (0);
seq__7371 = G__7414;
chunk__7372 = G__7415;
count__7373 = G__7416;
i__7374 = G__7417;
continue;
}
} else {
return null;
}
}
break;
}
});
speclj.running.nested_fns = (function speclj$running$nested_fns(base,fns){
if(cljs.core.seq.call(null,fns)){
return cljs.core.partial.call(null,cljs.core.first.call(null,fns),speclj.running.nested_fns.call(null,base,cljs.core.rest.call(null,fns)));
} else {
return base;
}
});
speclj.running.eval_characteristic = (function speclj$running$eval_characteristic(befores,body,afters){
speclj.running.eval_components.call(null,befores);

try{return body.call(null);
}finally {speclj.running.eval_components.call(null,afters);
}});
speclj.running.reset_withs = (function speclj$running$reset_withs(withs){
var seq__7404 = cljs.core.seq.call(null,withs);
var chunk__7405 = null;
var count__7406 = (0);
var i__7407 = (0);
while(true){
if((i__7407 < count__7406)){
var with$ = cljs.core._nth.call(null,chunk__7405,i__7407);
speclj.components.reset_with.call(null,with$);


var G__7427 = seq__7404;
var G__7428 = chunk__7405;
var G__7429 = count__7406;
var G__7430 = (i__7407 + (1));
seq__7404 = G__7427;
chunk__7405 = G__7428;
count__7406 = G__7429;
i__7407 = G__7430;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7404);
if(temp__5825__auto__){
var seq__7404__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7404__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7404__$1);
var G__7433 = cljs.core.chunk_rest.call(null,seq__7404__$1);
var G__7434 = c__5548__auto__;
var G__7435 = cljs.core.count.call(null,c__5548__auto__);
var G__7436 = (0);
seq__7404 = G__7433;
chunk__7405 = G__7434;
count__7406 = G__7435;
i__7407 = G__7436;
continue;
} else {
var with$ = cljs.core.first.call(null,seq__7404__$1);
speclj.components.reset_with.call(null,with$);


var G__7437 = cljs.core.next.call(null,seq__7404__$1);
var G__7438 = null;
var G__7439 = (0);
var G__7440 = (0);
seq__7404 = G__7437;
chunk__7405 = G__7438;
count__7406 = G__7439;
i__7407 = G__7440;
continue;
}
} else {
return null;
}
}
break;
}
});
speclj.running.collect_components = (function speclj$running$collect_components(getter,description){
var description__$1 = description;
var components = cljs.core.PersistentVector.EMPTY;
while(true){
if(cljs.core.truth_(description__$1)){
var G__7446 = cljs.core.deref.call(null,description__$1.parent);
var G__7447 = cljs.core.concat.call(null,getter.call(null,description__$1),components);
description__$1 = G__7446;
components = G__7447;
continue;
} else {
return components;
}
break;
}
});
speclj.running.report_result = (function speclj$running$report_result(result_constructor,characteristic,start_time,reporters,failure){
var present_args = cljs.core.filter.call(null,cljs.core.identity,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [characteristic,speclj.platform.secs_since.call(null,start_time),failure], null));
var result = cljs.core.apply.call(null,result_constructor,present_args);
speclj.reporting.report_run.call(null,result,reporters);

return result;
});
speclj.running.do_characteristic = (function speclj$running$do_characteristic(characteristic,reporters){
var description = cljs.core.deref.call(null,characteristic.parent);
var befores = speclj.running.collect_components.call(null,(function (p1__7443_SHARP_){
return cljs.core.deref.call(null,p1__7443_SHARP_.befores);
}),description);
var afters = speclj.running.collect_components.call(null,(function (p1__7444_SHARP_){
return cljs.core.deref.call(null,p1__7444_SHARP_.afters);
}),description);
var core_body = characteristic.body;
var before_and_after_body = (function (){
return speclj.running.eval_characteristic.call(null,befores,core_body,afters);
});
var arounds = speclj.running.collect_components.call(null,(function (p1__7445_SHARP_){
return cljs.core.deref.call(null,p1__7445_SHARP_.arounds);
}),description);
var full_body = speclj.running.nested_fns.call(null,before_and_after_body,cljs.core.map.call(null,(function (p1__7448_SHARP_){
return p1__7448_SHARP_.body;
}),arounds));
var withs = speclj.running.collect_components.call(null,(function (p1__7449_SHARP_){
return cljs.core.deref.call(null,p1__7449_SHARP_.withs);
}),description);
var start_time = speclj.platform.current_time.call(null);
try{full_body.call(null);

return speclj.running.report_result.call(null,speclj.results.pass_result,characteristic,start_time,reporters,null);
}catch (e7452){var e = e7452;
if(speclj.platform.pending_QMARK_.call(null,e)){
return speclj.running.report_result.call(null,speclj.results.pending_result,characteristic,start_time,reporters,e);
} else {
return speclj.running.report_result.call(null,speclj.results.fail_result,characteristic,start_time,reporters,e);
}
}finally {speclj.running.reset_withs.call(null,withs);
}});
speclj.running.do_characteristics = (function speclj$running$do_characteristics(characteristics,reporters){
return cljs.core.doall.call(null,(function (){var iter__5503__auto__ = (function speclj$running$do_characteristics_$_iter__7455(s__7456){
return (new cljs.core.LazySeq(null,(function (){
var s__7456__$1 = s__7456;
while(true){
var temp__5825__auto__ = cljs.core.seq.call(null,s__7456__$1);
if(temp__5825__auto__){
var s__7456__$2 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,s__7456__$2)){
var c__5501__auto__ = cljs.core.chunk_first.call(null,s__7456__$2);
var size__5502__auto__ = cljs.core.count.call(null,c__5501__auto__);
var b__7458 = cljs.core.chunk_buffer.call(null,size__5502__auto__);
if((function (){var i__7457 = (0);
while(true){
if((i__7457 < size__5502__auto__)){
var characteristic = cljs.core._nth.call(null,c__5501__auto__,i__7457);
if(cljs.core.truth_(speclj.running.can_run_QMARK_.call(null,characteristic))){
cljs.core.chunk_append.call(null,b__7458,speclj.running.do_characteristic.call(null,characteristic,reporters));

var G__7468 = (i__7457 + (1));
i__7457 = G__7468;
continue;
} else {
var G__7469 = (i__7457 + (1));
i__7457 = G__7469;
continue;
}
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__7458),speclj$running$do_characteristics_$_iter__7455.call(null,cljs.core.chunk_rest.call(null,s__7456__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__7458),null);
}
} else {
var characteristic = cljs.core.first.call(null,s__7456__$2);
if(cljs.core.truth_(speclj.running.can_run_QMARK_.call(null,characteristic))){
return cljs.core.cons.call(null,speclj.running.do_characteristic.call(null,characteristic,reporters),speclj$running$do_characteristics_$_iter__7455.call(null,cljs.core.rest.call(null,s__7456__$2)));
} else {
var G__7474 = cljs.core.rest.call(null,s__7456__$2);
s__7456__$1 = G__7474;
continue;
}
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5503__auto__.call(null,characteristics);
})());
});
speclj.running.do_child_contexts = (function speclj$running$do_child_contexts(context,results,reporters){
var results__$1 = results;
var children = cljs.core.deref.call(null,context.children);
while(true){
if(cljs.core.seq.call(null,children)){
var G__7477 = cljs.core.concat.call(null,results__$1,speclj.running.do_description.call(null,cljs.core.first.call(null,children),reporters));
var G__7478 = cljs.core.rest.call(null,children);
results__$1 = G__7477;
children = G__7478;
continue;
} else {
speclj.running.eval_components.call(null,cljs.core.deref.call(null,context.after_alls));

return results__$1;
}
break;
}
});
speclj.running.results_for_context = (function speclj$running$results_for_context(context,reporters){
if(cljs.core.truth_(speclj.tags.pass_tag_filter_QMARK_.call(null,speclj.tags.tags_for.call(null,context)))){
return speclj.running.do_characteristics.call(null,cljs.core.deref.call(null,context.characteristics),reporters);
} else {
return cljs.core.PersistentVector.EMPTY;
}
});
speclj.running.with_withs_bound = (function speclj$running$with_withs_bound(description,body){
var withs = cljs.core.concat.call(null,cljs.core.deref.call(null,description.withs),cljs.core.deref.call(null,description.with_alls));
var ns = clojure.string.replace.call(null,description.ns,"-","_");
var var_names = cljs.core.map.call(null,(function (p1__7472_SHARP_){
return [ns,".",cljs.core.name.call(null,p1__7472_SHARP_.name)].join('');
}),withs);
var unique_names = cljs.core.map.call(null,(function (p1__7473_SHARP_){
return [ns,".",cljs.core.name.call(null,p1__7473_SHARP_.unique_name)].join('');
}),withs);
var seq__7480_7516 = cljs.core.seq.call(null,cljs.core.partition.call(null,(2),cljs.core.interleave.call(null,var_names,unique_names)));
var chunk__7481_7517 = null;
var count__7482_7518 = (0);
var i__7483_7519 = (0);
while(true){
if((i__7483_7519 < count__7482_7518)){
var vec__7492_7520 = cljs.core._nth.call(null,chunk__7481_7517,i__7483_7519);
var n_7521 = cljs.core.nth.call(null,vec__7492_7520,(0),null);
var un_7522 = cljs.core.nth.call(null,vec__7492_7520,(1),null);
var code_7524 = [cljs.core.str.cljs$core$IFn$_invoke$arity$1(n_7521)," = ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(un_7522),";"].join('');
eval(code_7524);


var G__7525 = seq__7480_7516;
var G__7526 = chunk__7481_7517;
var G__7527 = count__7482_7518;
var G__7528 = (i__7483_7519 + (1));
seq__7480_7516 = G__7525;
chunk__7481_7517 = G__7526;
count__7482_7518 = G__7527;
i__7483_7519 = G__7528;
continue;
} else {
var temp__5825__auto___7529 = cljs.core.seq.call(null,seq__7480_7516);
if(temp__5825__auto___7529){
var seq__7480_7530__$1 = temp__5825__auto___7529;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7480_7530__$1)){
var c__5548__auto___7531 = cljs.core.chunk_first.call(null,seq__7480_7530__$1);
var G__7532 = cljs.core.chunk_rest.call(null,seq__7480_7530__$1);
var G__7533 = c__5548__auto___7531;
var G__7534 = cljs.core.count.call(null,c__5548__auto___7531);
var G__7535 = (0);
seq__7480_7516 = G__7532;
chunk__7481_7517 = G__7533;
count__7482_7518 = G__7534;
i__7483_7519 = G__7535;
continue;
} else {
var vec__7495_7536 = cljs.core.first.call(null,seq__7480_7530__$1);
var n_7537 = cljs.core.nth.call(null,vec__7495_7536,(0),null);
var un_7538 = cljs.core.nth.call(null,vec__7495_7536,(1),null);
var code_7539 = [cljs.core.str.cljs$core$IFn$_invoke$arity$1(n_7537)," = ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(un_7538),";"].join('');
eval(code_7539);


var G__7540 = cljs.core.next.call(null,seq__7480_7530__$1);
var G__7541 = null;
var G__7542 = (0);
var G__7543 = (0);
seq__7480_7516 = G__7540;
chunk__7481_7517 = G__7541;
count__7482_7518 = G__7542;
i__7483_7519 = G__7543;
continue;
}
} else {
}
}
break;
}

try{return body.call(null);
}finally {var seq__7498_7544 = cljs.core.seq.call(null,var_names);
var chunk__7499_7545 = null;
var count__7500_7546 = (0);
var i__7501_7547 = (0);
while(true){
if((i__7501_7547 < count__7500_7546)){
var vec__7509_7548 = cljs.core._nth.call(null,chunk__7499_7545,i__7501_7547);
var n_7549 = cljs.core.nth.call(null,vec__7509_7548,(0),null);
var code_7550 = [cljs.core.str.cljs$core$IFn$_invoke$arity$1(n_7549)," = null;"].join('');
eval(code_7550);


var G__7551 = seq__7498_7544;
var G__7552 = chunk__7499_7545;
var G__7553 = count__7500_7546;
var G__7554 = (i__7501_7547 + (1));
seq__7498_7544 = G__7551;
chunk__7499_7545 = G__7552;
count__7500_7546 = G__7553;
i__7501_7547 = G__7554;
continue;
} else {
var temp__5825__auto___7555 = cljs.core.seq.call(null,seq__7498_7544);
if(temp__5825__auto___7555){
var seq__7498_7556__$1 = temp__5825__auto___7555;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7498_7556__$1)){
var c__5548__auto___7557 = cljs.core.chunk_first.call(null,seq__7498_7556__$1);
var G__7559 = cljs.core.chunk_rest.call(null,seq__7498_7556__$1);
var G__7560 = c__5548__auto___7557;
var G__7561 = cljs.core.count.call(null,c__5548__auto___7557);
var G__7562 = (0);
seq__7498_7544 = G__7559;
chunk__7499_7545 = G__7560;
count__7500_7546 = G__7561;
i__7501_7547 = G__7562;
continue;
} else {
var vec__7512_7563 = cljs.core.first.call(null,seq__7498_7556__$1);
var n_7564 = cljs.core.nth.call(null,vec__7512_7563,(0),null);
var code_7565 = [cljs.core.str.cljs$core$IFn$_invoke$arity$1(n_7564)," = null;"].join('');
eval(code_7565);


var G__7566 = cljs.core.next.call(null,seq__7498_7556__$1);
var G__7567 = null;
var G__7568 = (0);
var G__7569 = (0);
seq__7498_7544 = G__7566;
chunk__7499_7545 = G__7567;
count__7500_7546 = G__7568;
i__7501_7547 = G__7569;
continue;
}
} else {
}
}
break;
}
}});
speclj.running.nested_results_for_context = (function speclj$running$nested_results_for_context(description,reporters){
var results = speclj.running.results_for_context.call(null,description,reporters);
return speclj.running.do_child_contexts.call(null,description,results,reporters);
});
speclj.running.with_around_alls = (function speclj$running$with_around_alls(description,run_characteristics_fn){
return speclj.running.nested_fns.call(null,run_characteristics_fn,cljs.core.map.call(null,(function (p1__7515_SHARP_){
return p1__7515_SHARP_.body;
}),cljs.core.deref.call(null,description.around_alls))).call(null);
});
speclj.running.do_description = (function speclj$running$do_description(description,reporters){
if(cljs.core.truth_(speclj.running.can_run_QMARK_.call(null,description))){
var tag_sets = speclj.tags.tag_sets_for.call(null,description);
if(cljs.core.truth_(cljs.core.some.call(null,speclj.tags.pass_tag_filter_QMARK_,tag_sets))){
speclj.reporting.report_description_STAR_.call(null,reporters,description);

return speclj.running.with_withs_bound.call(null,description,(function (){
speclj.running.eval_components.call(null,cljs.core.deref.call(null,description.before_alls));

try{return speclj.running.with_around_alls.call(null,description,cljs.core.partial.call(null,speclj.running.nested_results_for_context,description,reporters));
}finally {speclj.running.reset_withs.call(null,cljs.core.deref.call(null,description.with_alls));
}}));
} else {
return null;
}
} else {
return null;
}
});
speclj.running.process_compile_error = (function speclj$running$process_compile_error(runner,e){
var error_result = speclj.results.error_result.call(null,e);
cljs.core.swap_BANG_.call(null,runner.results,cljs.core.conj,error_result);

return speclj.reporting.report_run.call(null,error_result,speclj.config.active_reporters.call(null));
});

/**
 * @interface
 */
speclj.running.Runner = function(){};

var speclj$running$Runner$run_directories$dyn_7570 = (function (this$,directories,reporters){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.running.run_directories[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,directories,reporters);
} else {
var m__5372__auto__ = (speclj.running.run_directories["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,directories,reporters);
} else {
throw cljs.core.missing_protocol.call(null,"Runner.run-directories",this$);
}
}
});
speclj.running.run_directories = (function speclj$running$run_directories(this$,directories,reporters){
if((((!((this$ == null)))) && ((!((this$.speclj$running$Runner$run_directories$arity$3 == null)))))){
return this$.speclj$running$Runner$run_directories$arity$3(this$,directories,reporters);
} else {
return speclj$running$Runner$run_directories$dyn_7570.call(null,this$,directories,reporters);
}
});

var speclj$running$Runner$submit_description$dyn_7571 = (function (this$,description){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.running.submit_description[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,description);
} else {
var m__5372__auto__ = (speclj.running.submit_description["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,description);
} else {
throw cljs.core.missing_protocol.call(null,"Runner.submit-description",this$);
}
}
});
speclj.running.submit_description = (function speclj$running$submit_description(this$,description){
if((((!((this$ == null)))) && ((!((this$.speclj$running$Runner$submit_description$arity$2 == null)))))){
return this$.speclj$running$Runner$submit_description$arity$2(this$,description);
} else {
return speclj$running$Runner$submit_description$dyn_7571.call(null,this$,description);
}
});

var speclj$running$Runner$run_description$dyn_7573 = (function (this$,description,reporters){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.running.run_description[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,description,reporters);
} else {
var m__5372__auto__ = (speclj.running.run_description["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,description,reporters);
} else {
throw cljs.core.missing_protocol.call(null,"Runner.run-description",this$);
}
}
});
speclj.running.run_description = (function speclj$running$run_description(this$,description,reporters){
if((((!((this$ == null)))) && ((!((this$.speclj$running$Runner$run_description$arity$3 == null)))))){
return this$.speclj$running$Runner$run_description$arity$3(this$,description,reporters);
} else {
return speclj$running$Runner$run_description$dyn_7573.call(null,this$,description,reporters);
}
});

var speclj$running$Runner$run_and_report$dyn_7575 = (function (this$,reporters){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.running.run_and_report[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,reporters);
} else {
var m__5372__auto__ = (speclj.running.run_and_report["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,reporters);
} else {
throw cljs.core.missing_protocol.call(null,"Runner.run-and-report",this$);
}
}
});
speclj.running.run_and_report = (function speclj$running$run_and_report(this$,reporters){
if((((!((this$ == null)))) && ((!((this$.speclj$running$Runner$run_and_report$arity$2 == null)))))){
return this$.speclj$running$Runner$run_and_report$arity$2(this$,reporters);
} else {
return speclj$running$Runner$run_and_report$dyn_7575.call(null,this$,reporters);
}
});


//# sourceMappingURL=running.js.map
