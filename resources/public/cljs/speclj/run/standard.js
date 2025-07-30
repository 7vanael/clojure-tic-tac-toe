// Compiled by ClojureScript 1.12.42 {:optimizations :none}
goog.provide('speclj.run.standard');
goog.require('cljs.core');
goog.require('speclj.components');
goog.require('speclj.config');
goog.require('speclj.report.progress');
goog.require('speclj.reporting');
goog.require('speclj.results');
goog.require('speclj.running');
goog.require('speclj.tags');
speclj.run.standard.counter = cljs.core.atom.call(null,(0));

/**
* @constructor
 * @implements {speclj.running.Runner}
*/
speclj.run.standard.StandardRunner = (function (num,descriptions,results){
this.num = num;
this.descriptions = descriptions;
this.results = results;
});
(speclj.run.standard.StandardRunner.prototype.speclj$running$Runner$ = cljs.core.PROTOCOL_SENTINEL);

(speclj.run.standard.StandardRunner.prototype.speclj$running$Runner$run_directories$arity$3 = (function (this$,directories,reporters){
var self__ = this;
var this$__$1 = this;
return alert("StandardRunner.run-directories:  I don't know how to do this.");
}));

(speclj.run.standard.StandardRunner.prototype.speclj$running$Runner$submit_description$arity$2 = (function (this$,description){
var self__ = this;
var this$__$1 = this;
return cljs.core.swap_BANG_.call(null,self__.descriptions,cljs.core.conj,description);
}));

(speclj.run.standard.StandardRunner.prototype.speclj$running$Runner$run_description$arity$3 = (function (this$,description,reporters){
var self__ = this;
var this$__$1 = this;
var run_results = speclj.running.do_description.call(null,description,reporters);
return cljs.core.swap_BANG_.call(null,self__.results,cljs.core.into,run_results);
}));

(speclj.run.standard.StandardRunner.prototype.speclj$running$Runner$run_and_report$arity$2 = (function (this$,reporters){
var self__ = this;
var this$__$1 = this;
var seq__7668_7672 = cljs.core.seq.call(null,speclj.running.filter_focused.call(null,cljs.core.deref.call(null,self__.descriptions)));
var chunk__7669_7673 = null;
var count__7670_7674 = (0);
var i__7671_7675 = (0);
while(true){
if((i__7671_7675 < count__7670_7674)){
var description_7676 = cljs.core._nth.call(null,chunk__7669_7673,i__7671_7675);
speclj.running.run_description.call(null,this$__$1,description_7676,reporters);


var G__7677 = seq__7668_7672;
var G__7678 = chunk__7669_7673;
var G__7679 = count__7670_7674;
var G__7680 = (i__7671_7675 + (1));
seq__7668_7672 = G__7677;
chunk__7669_7673 = G__7678;
count__7670_7674 = G__7679;
i__7671_7675 = G__7680;
continue;
} else {
var temp__5825__auto___7681 = cljs.core.seq.call(null,seq__7668_7672);
if(temp__5825__auto___7681){
var seq__7668_7682__$1 = temp__5825__auto___7681;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7668_7682__$1)){
var c__5548__auto___7683 = cljs.core.chunk_first.call(null,seq__7668_7682__$1);
var G__7684 = cljs.core.chunk_rest.call(null,seq__7668_7682__$1);
var G__7685 = c__5548__auto___7683;
var G__7686 = cljs.core.count.call(null,c__5548__auto___7683);
var G__7687 = (0);
seq__7668_7672 = G__7684;
chunk__7669_7673 = G__7685;
count__7670_7674 = G__7686;
i__7671_7675 = G__7687;
continue;
} else {
var description_7688 = cljs.core.first.call(null,seq__7668_7682__$1);
speclj.running.run_description.call(null,this$__$1,description_7688,reporters);


var G__7689 = cljs.core.next.call(null,seq__7668_7682__$1);
var G__7690 = null;
var G__7691 = (0);
var G__7692 = (0);
seq__7668_7672 = G__7689;
chunk__7669_7673 = G__7690;
count__7670_7674 = G__7691;
i__7671_7675 = G__7692;
continue;
}
} else {
}
}
break;
}

return speclj.reporting.report_runs_STAR_.call(null,reporters,cljs.core.deref.call(null,self__.results));
}));

(speclj.run.standard.StandardRunner.getBasis = (function (){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"num","num",-669195096,null),new cljs.core.Symbol(null,"descriptions","descriptions",817338403,null),new cljs.core.Symbol(null,"results","results",506361414,null)], null);
}));

(speclj.run.standard.StandardRunner.cljs$lang$type = true);

(speclj.run.standard.StandardRunner.cljs$lang$ctorStr = "speclj.run.standard/StandardRunner");

(speclj.run.standard.StandardRunner.cljs$lang$ctorPrWriter = (function (this__5310__auto__,writer__5311__auto__,opt__5312__auto__){
return cljs.core._write.call(null,writer__5311__auto__,"speclj.run.standard/StandardRunner");
}));

/**
 * Positional factory function for speclj.run.standard/StandardRunner.
 */
speclj.run.standard.__GT_StandardRunner = (function speclj$run$standard$__GT_StandardRunner(num,descriptions,results){
return (new speclj.run.standard.StandardRunner(num,descriptions,results));
});

(speclj.run.standard.StandardRunner.prototype.cljs$core$IPrintWithWriter$ = cljs.core.PROTOCOL_SENTINEL);

(speclj.run.standard.StandardRunner.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (x,writer,opts){
var x__$1 = this;
cljs.core._write.call(null,writer,["#<speclj.run.standard.StandardRunner(num: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(x__$1.num),", descriptions: "].join(''));

cljs.core._pr_writer.call(null,cljs.core.deref.call(null,x__$1.descriptions),writer,opts);

return cljs.core._write.call(null,writer,")>");
}));

(speclj.components.Description.prototype.cljs$core$IPrintWithWriter$ = cljs.core.PROTOCOL_SENTINEL);

(speclj.components.Description.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (x,writer,opts){
var x__$1 = this;
return cljs.core._write.call(null,writer,["#<speclj.component.Description(name: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(x__$1.name),")>"].join(''));
}));
speclj.run.standard.new_standard_runner = (function speclj$run$standard$new_standard_runner(){
return (new speclj.run.standard.StandardRunner(cljs.core.swap_BANG_.call(null,speclj.run.standard.counter,cljs.core.inc),cljs.core.atom.call(null,cljs.core.PersistentVector.EMPTY),cljs.core.atom.call(null,cljs.core.PersistentVector.EMPTY)));
});
cljs.core.reset_BANG_.call(null,speclj.config.default_runner_fn,speclj.run.standard.new_standard_runner);
cljs.core.reset_BANG_.call(null,speclj.config.default_runner,speclj.run.standard.new_standard_runner.call(null));
speclj.run.standard.armed = false;
speclj.run.standard.run_specs = (function speclj$run$standard$run_specs(var_args){
var args__5755__auto__ = [];
var len__5749__auto___7694 = arguments.length;
var i__5750__auto___7695 = (0);
while(true){
if((i__5750__auto___7695 < len__5749__auto___7694)){
args__5755__auto__.push((arguments[i__5750__auto___7695]));

var G__7696 = (i__5750__auto___7695 + (1));
i__5750__auto___7695 = G__7696;
continue;
} else {
}
break;
}

var argseq__5756__auto__ = ((((0) < args__5755__auto__.length))?(new cljs.core.IndexedSeq(args__5755__auto__.slice((0)),(0),null)):null);
return speclj.run.standard.run_specs.cljs$core$IFn$_invoke$arity$variadic(argseq__5756__auto__);
});

(speclj.run.standard.run_specs.cljs$core$IFn$_invoke$arity$variadic = (function (configurations){

if(speclj.run.standard.armed){
return speclj.config.with_config.call(null,cljs.core.merge.call(null,cljs.core.dissoc.call(null,speclj.config.default_config,new cljs.core.Keyword(null,"runner","runner",1945441304)),cljs.core.apply.call(null,cljs.core.hash_map,configurations)),(function (){
var temp__5823__auto___7697 = speclj.tags.describe_filter.call(null);
if(cljs.core.truth_(temp__5823__auto___7697)){
var filter_msg_7698 = temp__5823__auto___7697;
speclj.reporting.report_message_STAR_.call(null,speclj.config.active_reporters.call(null),filter_msg_7698);
} else {
}

speclj.running.run_and_report.call(null,speclj.config.active_runner.call(null),speclj.config.active_reporters.call(null));

return speclj.results.fail_count.call(null,cljs.core.deref.call(null,speclj.config.active_runner.call(null).results));
}));
} else {
return null;
}
}));

(speclj.run.standard.run_specs.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(speclj.run.standard.run_specs.cljs$lang$applyTo = (function (seq7693){
var self__5735__auto__ = this;
return self__5735__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq.call(null,seq7693));
}));


//# sourceMappingURL=standard.js.map
