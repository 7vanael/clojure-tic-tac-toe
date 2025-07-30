// Compiled by ClojureScript 1.12.42 {:optimizations :none}
goog.provide('speclj.reporting');
goog.require('cljs.core');
goog.require('clojure.string');
goog.require('goog.string');
goog.require('speclj.config');
goog.require('speclj.platform');
goog.require('speclj.results');
speclj.reporting.tally_time = (function speclj$reporting$tally_time(results){
return cljs.core.apply.call(null,cljs.core._PLUS_,cljs.core.map.call(null,(function (p1__6953_SHARP_){
return p1__6953_SHARP_.seconds;
}),results));
});

/**
 * @interface
 */
speclj.reporting.Reporter = function(){};

var speclj$reporting$Reporter$report_message$dyn_6977 = (function (reporter,message){
var x__5373__auto__ = (((reporter == null))?null:reporter);
var m__5374__auto__ = (speclj.reporting.report_message[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,reporter,message);
} else {
var m__5372__auto__ = (speclj.reporting.report_message["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,reporter,message);
} else {
throw cljs.core.missing_protocol.call(null,"Reporter.report-message",reporter);
}
}
});
speclj.reporting.report_message = (function speclj$reporting$report_message(reporter,message){
if((((!((reporter == null)))) && ((!((reporter.speclj$reporting$Reporter$report_message$arity$2 == null)))))){
return reporter.speclj$reporting$Reporter$report_message$arity$2(reporter,message);
} else {
return speclj$reporting$Reporter$report_message$dyn_6977.call(null,reporter,message);
}
});

var speclj$reporting$Reporter$report_description$dyn_6981 = (function (this$,description){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.reporting.report_description[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,description);
} else {
var m__5372__auto__ = (speclj.reporting.report_description["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,description);
} else {
throw cljs.core.missing_protocol.call(null,"Reporter.report-description",this$);
}
}
});
speclj.reporting.report_description = (function speclj$reporting$report_description(this$,description){
if((((!((this$ == null)))) && ((!((this$.speclj$reporting$Reporter$report_description$arity$2 == null)))))){
return this$.speclj$reporting$Reporter$report_description$arity$2(this$,description);
} else {
return speclj$reporting$Reporter$report_description$dyn_6981.call(null,this$,description);
}
});

var speclj$reporting$Reporter$report_pass$dyn_6983 = (function (this$,result){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.reporting.report_pass[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,result);
} else {
var m__5372__auto__ = (speclj.reporting.report_pass["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,result);
} else {
throw cljs.core.missing_protocol.call(null,"Reporter.report-pass",this$);
}
}
});
speclj.reporting.report_pass = (function speclj$reporting$report_pass(this$,result){
if((((!((this$ == null)))) && ((!((this$.speclj$reporting$Reporter$report_pass$arity$2 == null)))))){
return this$.speclj$reporting$Reporter$report_pass$arity$2(this$,result);
} else {
return speclj$reporting$Reporter$report_pass$dyn_6983.call(null,this$,result);
}
});

var speclj$reporting$Reporter$report_pending$dyn_6988 = (function (this$,result){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.reporting.report_pending[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,result);
} else {
var m__5372__auto__ = (speclj.reporting.report_pending["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,result);
} else {
throw cljs.core.missing_protocol.call(null,"Reporter.report-pending",this$);
}
}
});
speclj.reporting.report_pending = (function speclj$reporting$report_pending(this$,result){
if((((!((this$ == null)))) && ((!((this$.speclj$reporting$Reporter$report_pending$arity$2 == null)))))){
return this$.speclj$reporting$Reporter$report_pending$arity$2(this$,result);
} else {
return speclj$reporting$Reporter$report_pending$dyn_6988.call(null,this$,result);
}
});

var speclj$reporting$Reporter$report_fail$dyn_6991 = (function (this$,result){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.reporting.report_fail[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,result);
} else {
var m__5372__auto__ = (speclj.reporting.report_fail["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,result);
} else {
throw cljs.core.missing_protocol.call(null,"Reporter.report-fail",this$);
}
}
});
speclj.reporting.report_fail = (function speclj$reporting$report_fail(this$,result){
if((((!((this$ == null)))) && ((!((this$.speclj$reporting$Reporter$report_fail$arity$2 == null)))))){
return this$.speclj$reporting$Reporter$report_fail$arity$2(this$,result);
} else {
return speclj$reporting$Reporter$report_fail$dyn_6991.call(null,this$,result);
}
});

var speclj$reporting$Reporter$report_runs$dyn_6993 = (function (this$,results){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.reporting.report_runs[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,results);
} else {
var m__5372__auto__ = (speclj.reporting.report_runs["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,results);
} else {
throw cljs.core.missing_protocol.call(null,"Reporter.report-runs",this$);
}
}
});
speclj.reporting.report_runs = (function speclj$reporting$report_runs(this$,results){
if((((!((this$ == null)))) && ((!((this$.speclj$reporting$Reporter$report_runs$arity$2 == null)))))){
return this$.speclj$reporting$Reporter$report_runs$arity$2(this$,results);
} else {
return speclj$reporting$Reporter$report_runs$dyn_6993.call(null,this$,results);
}
});

var speclj$reporting$Reporter$report_error$dyn_6999 = (function (this$,exception){
var x__5373__auto__ = (((this$ == null))?null:this$);
var m__5374__auto__ = (speclj.reporting.report_error[goog.typeOf(x__5373__auto__)]);
if((!((m__5374__auto__ == null)))){
return m__5374__auto__.call(null,this$,exception);
} else {
var m__5372__auto__ = (speclj.reporting.report_error["_"]);
if((!((m__5372__auto__ == null)))){
return m__5372__auto__.call(null,this$,exception);
} else {
throw cljs.core.missing_protocol.call(null,"Reporter.report-error",this$);
}
}
});
speclj.reporting.report_error = (function speclj$reporting$report_error(this$,exception){
if((((!((this$ == null)))) && ((!((this$.speclj$reporting$Reporter$report_error$arity$2 == null)))))){
return this$.speclj$reporting$Reporter$report_error$arity$2(this$,exception);
} else {
return speclj$reporting$Reporter$report_error$dyn_6999.call(null,this$,exception);
}
});

if((typeof speclj !== 'undefined') && (typeof speclj.reporting !== 'undefined') && (typeof speclj.reporting.report_run !== 'undefined')){
} else {
speclj.reporting.report_run = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"speclj.reporting","report-run"),(function (result,reporters){
return cljs.core.type.call(null,result);
}),new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
cljs.core._add_method.call(null,speclj.reporting.report_run,speclj.results.PassResult,(function (result,reporters){
var seq__6984 = cljs.core.seq.call(null,reporters);
var chunk__6985 = null;
var count__6986 = (0);
var i__6987 = (0);
while(true){
if((i__6987 < count__6986)){
var reporter = cljs.core._nth.call(null,chunk__6985,i__6987);
speclj.reporting.report_pass.call(null,reporter,result);


var G__7029 = seq__6984;
var G__7030 = chunk__6985;
var G__7031 = count__6986;
var G__7032 = (i__6987 + (1));
seq__6984 = G__7029;
chunk__6985 = G__7030;
count__6986 = G__7031;
i__6987 = G__7032;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__6984);
if(temp__5825__auto__){
var seq__6984__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__6984__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__6984__$1);
var G__7033 = cljs.core.chunk_rest.call(null,seq__6984__$1);
var G__7034 = c__5548__auto__;
var G__7035 = cljs.core.count.call(null,c__5548__auto__);
var G__7036 = (0);
seq__6984 = G__7033;
chunk__6985 = G__7034;
count__6986 = G__7035;
i__6987 = G__7036;
continue;
} else {
var reporter = cljs.core.first.call(null,seq__6984__$1);
speclj.reporting.report_pass.call(null,reporter,result);


var G__7037 = cljs.core.next.call(null,seq__6984__$1);
var G__7038 = null;
var G__7039 = (0);
var G__7040 = (0);
seq__6984 = G__7037;
chunk__6985 = G__7038;
count__6986 = G__7039;
i__6987 = G__7040;
continue;
}
} else {
return null;
}
}
break;
}
}));
cljs.core._add_method.call(null,speclj.reporting.report_run,speclj.results.FailResult,(function (result,reporters){
var seq__7000 = cljs.core.seq.call(null,reporters);
var chunk__7001 = null;
var count__7002 = (0);
var i__7003 = (0);
while(true){
if((i__7003 < count__7002)){
var reporter = cljs.core._nth.call(null,chunk__7001,i__7003);
speclj.reporting.report_fail.call(null,reporter,result);


var G__7043 = seq__7000;
var G__7044 = chunk__7001;
var G__7045 = count__7002;
var G__7046 = (i__7003 + (1));
seq__7000 = G__7043;
chunk__7001 = G__7044;
count__7002 = G__7045;
i__7003 = G__7046;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7000);
if(temp__5825__auto__){
var seq__7000__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7000__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7000__$1);
var G__7047 = cljs.core.chunk_rest.call(null,seq__7000__$1);
var G__7048 = c__5548__auto__;
var G__7049 = cljs.core.count.call(null,c__5548__auto__);
var G__7050 = (0);
seq__7000 = G__7047;
chunk__7001 = G__7048;
count__7002 = G__7049;
i__7003 = G__7050;
continue;
} else {
var reporter = cljs.core.first.call(null,seq__7000__$1);
speclj.reporting.report_fail.call(null,reporter,result);


var G__7051 = cljs.core.next.call(null,seq__7000__$1);
var G__7052 = null;
var G__7053 = (0);
var G__7054 = (0);
seq__7000 = G__7051;
chunk__7001 = G__7052;
count__7002 = G__7053;
i__7003 = G__7054;
continue;
}
} else {
return null;
}
}
break;
}
}));
cljs.core._add_method.call(null,speclj.reporting.report_run,speclj.results.PendingResult,(function (result,reporters){
var seq__7015 = cljs.core.seq.call(null,reporters);
var chunk__7016 = null;
var count__7017 = (0);
var i__7018 = (0);
while(true){
if((i__7018 < count__7017)){
var reporter = cljs.core._nth.call(null,chunk__7016,i__7018);
speclj.reporting.report_pending.call(null,reporter,result);


var G__7056 = seq__7015;
var G__7057 = chunk__7016;
var G__7058 = count__7017;
var G__7059 = (i__7018 + (1));
seq__7015 = G__7056;
chunk__7016 = G__7057;
count__7017 = G__7058;
i__7018 = G__7059;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7015);
if(temp__5825__auto__){
var seq__7015__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7015__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7015__$1);
var G__7060 = cljs.core.chunk_rest.call(null,seq__7015__$1);
var G__7061 = c__5548__auto__;
var G__7062 = cljs.core.count.call(null,c__5548__auto__);
var G__7063 = (0);
seq__7015 = G__7060;
chunk__7016 = G__7061;
count__7017 = G__7062;
i__7018 = G__7063;
continue;
} else {
var reporter = cljs.core.first.call(null,seq__7015__$1);
speclj.reporting.report_pending.call(null,reporter,result);


var G__7064 = cljs.core.next.call(null,seq__7015__$1);
var G__7065 = null;
var G__7066 = (0);
var G__7067 = (0);
seq__7015 = G__7064;
chunk__7016 = G__7065;
count__7017 = G__7066;
i__7018 = G__7067;
continue;
}
} else {
return null;
}
}
break;
}
}));
cljs.core._add_method.call(null,speclj.reporting.report_run,speclj.results.ErrorResult,(function (result,reporters){
var seq__7025 = cljs.core.seq.call(null,reporters);
var chunk__7026 = null;
var count__7027 = (0);
var i__7028 = (0);
while(true){
if((i__7028 < count__7027)){
var reporter = cljs.core._nth.call(null,chunk__7026,i__7028);
speclj.reporting.report_error.call(null,reporter,result);


var G__7069 = seq__7025;
var G__7070 = chunk__7026;
var G__7071 = count__7027;
var G__7072 = (i__7028 + (1));
seq__7025 = G__7069;
chunk__7026 = G__7070;
count__7027 = G__7071;
i__7028 = G__7072;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7025);
if(temp__5825__auto__){
var seq__7025__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7025__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7025__$1);
var G__7073 = cljs.core.chunk_rest.call(null,seq__7025__$1);
var G__7074 = c__5548__auto__;
var G__7075 = cljs.core.count.call(null,c__5548__auto__);
var G__7076 = (0);
seq__7025 = G__7073;
chunk__7026 = G__7074;
count__7027 = G__7075;
i__7028 = G__7076;
continue;
} else {
var reporter = cljs.core.first.call(null,seq__7025__$1);
speclj.reporting.report_error.call(null,reporter,result);


var G__7077 = cljs.core.next.call(null,seq__7025__$1);
var G__7078 = null;
var G__7079 = (0);
var G__7080 = (0);
seq__7025 = G__7077;
chunk__7026 = G__7078;
count__7027 = G__7079;
i__7028 = G__7080;
continue;
}
} else {
return null;
}
}
break;
}
}));
speclj.reporting.stylizer = (function speclj$reporting$stylizer(code){
return (function (text){
if(cljs.core.truth_(speclj.config._STAR_color_QMARK__STAR_)){
return ["\u001B[",cljs.core.str.cljs$core$IFn$_invoke$arity$1(code),"m",cljs.core.str.cljs$core$IFn$_invoke$arity$1(text),"\u001B[0m"].join('');
} else {
return text;
}
});
});
speclj.reporting.red = speclj.reporting.stylizer.call(null,"31");
speclj.reporting.green = speclj.reporting.stylizer.call(null,"32");
speclj.reporting.yellow = speclj.reporting.stylizer.call(null,"33");
speclj.reporting.grey = speclj.reporting.stylizer.call(null,"90");
speclj.reporting.print_elides = (function speclj$reporting$print_elides(n){
if((n > (0))){
return cljs.core.println.call(null,"\t...",n,"stack levels elided ...");
} else {
return null;
}
});
speclj.reporting.print_stack_levels = (function speclj$reporting$print_stack_levels(exception){
var levels_7090 = speclj.platform.stack_trace.call(null,exception);
var elides_7091 = (0);
while(true){
if(cljs.core.seq.call(null,levels_7090)){
var level_7092 = cljs.core.first.call(null,levels_7090);
if(speclj.platform.elide_level_QMARK_.call(null,level_7092)){
var G__7093 = cljs.core.rest.call(null,levels_7090);
var G__7094 = (elides_7091 + (1));
levels_7090 = G__7093;
elides_7091 = G__7094;
continue;
} else {
speclj.reporting.print_elides.call(null,elides_7091);

cljs.core.println.call(null,cljs.core.str.cljs$core$IFn$_invoke$arity$1(level_7092));

var G__7096 = cljs.core.rest.call(null,levels_7090);
var G__7097 = (0);
levels_7090 = G__7096;
elides_7091 = G__7097;
continue;
}
} else {
speclj.reporting.print_elides.call(null,elides_7091);
}
break;
}

var temp__5823__auto__ = speclj.platform.cause.call(null,exception);
if(cljs.core.truth_(temp__5823__auto__)){
var cause = temp__5823__auto__;
return speclj.reporting.print_exception.call(null,"Caused by:",cause);
} else {
return null;
}
});
speclj.reporting.print_exception = (function speclj$reporting$print_exception(prefix,exception){
if(cljs.core.truth_(prefix)){
cljs.core.println.call(null,prefix,cljs.core.str.cljs$core$IFn$_invoke$arity$1(exception));
} else {
cljs.core.println.call(null,cljs.core.str.cljs$core$IFn$_invoke$arity$1(exception));
}

return speclj.reporting.print_stack_levels.call(null,exception);
});
speclj.reporting.stack_trace_str = (function speclj$reporting$stack_trace_str(exception){
var sb__5670__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__7081_7105 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__7082_7106 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__7083_7107 = true;
var _STAR_print_fn_STAR__temp_val__7084_7108 = (function (x__5671__auto__){
return sb__5670__auto__.append(x__5671__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__7083_7107);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__7084_7108);

try{if(cljs.core.truth_(speclj.config._STAR_full_stack_trace_QMARK__STAR_)){
speclj.platform.print_stack_trace.call(null,exception);
} else {
speclj.reporting.print_exception.call(null,null,exception);
}
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__7082_7106);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__7081_7105);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__5670__auto__);
});
speclj.reporting.prefix = (function speclj$reporting$prefix(var_args){
var args__5755__auto__ = [];
var len__5749__auto___7110 = arguments.length;
var i__5750__auto___7111 = (0);
while(true){
if((i__5750__auto___7111 < len__5749__auto___7110)){
args__5755__auto__.push((arguments[i__5750__auto___7111]));

var G__7112 = (i__5750__auto___7111 + (1));
i__5750__auto___7111 = G__7112;
continue;
} else {
}
break;
}

var argseq__5756__auto__ = ((((1) < args__5755__auto__.length))?(new cljs.core.IndexedSeq(args__5755__auto__.slice((1)),(0),null)):null);
return speclj.reporting.prefix.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5756__auto__);
});

(speclj.reporting.prefix.cljs$core$IFn$_invoke$arity$variadic = (function (pre,args){
var value = cljs.core.apply.call(null,cljs.core.str,args);
var lines = clojure.string.split.call(null,value,/[\r\n]+/);
var prefixed_lines = cljs.core.map.call(null,(function (p1__7086_SHARP_){
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(pre),cljs.core.str.cljs$core$IFn$_invoke$arity$1(p1__7086_SHARP_)].join('');
}),lines);
return clojure.string.join.call(null,speclj.platform.endl,prefixed_lines);
}));

(speclj.reporting.prefix.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(speclj.reporting.prefix.cljs$lang$applyTo = (function (seq7087){
var G__7088 = cljs.core.first.call(null,seq7087);
var seq7087__$1 = cljs.core.next.call(null,seq7087);
var self__5734__auto__ = this;
return self__5734__auto__.cljs$core$IFn$_invoke$arity$variadic(G__7088,seq7087__$1);
}));

speclj.reporting.indent = (function speclj$reporting$indent(var_args){
var args__5755__auto__ = [];
var len__5749__auto___7120 = arguments.length;
var i__5750__auto___7121 = (0);
while(true){
if((i__5750__auto___7121 < len__5749__auto___7120)){
args__5755__auto__.push((arguments[i__5750__auto___7121]));

var G__7122 = (i__5750__auto___7121 + (1));
i__5750__auto___7121 = G__7122;
continue;
} else {
}
break;
}

var argseq__5756__auto__ = ((((1) < args__5755__auto__.length))?(new cljs.core.IndexedSeq(args__5755__auto__.slice((1)),(0),null)):null);
return speclj.reporting.indent.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),argseq__5756__auto__);
});

(speclj.reporting.indent.cljs$core$IFn$_invoke$arity$variadic = (function (n,args){
var spaces = ((n * 2.0) | (0));
var indention = cljs.core.reduce.call(null,(function (p,_){
return [" ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(p)].join('');
}),"",cljs.core.range.call(null,spaces));
return cljs.core.apply.call(null,speclj.reporting.prefix,indention,args);
}));

(speclj.reporting.indent.cljs$lang$maxFixedArity = (1));

/** @this {Function} */
(speclj.reporting.indent.cljs$lang$applyTo = (function (seq7103){
var G__7104 = cljs.core.first.call(null,seq7103);
var seq7103__$1 = cljs.core.next.call(null,seq7103);
var self__5734__auto__ = this;
return self__5734__auto__.cljs$core$IFn$_invoke$arity$variadic(G__7104,seq7103__$1);
}));

speclj.reporting.report_description_STAR_ = (function speclj$reporting$report_description_STAR_(reporters,description){
var seq__7114 = cljs.core.seq.call(null,reporters);
var chunk__7115 = null;
var count__7116 = (0);
var i__7117 = (0);
while(true){
if((i__7117 < count__7116)){
var reporter = cljs.core._nth.call(null,chunk__7115,i__7117);
speclj.reporting.report_description.call(null,reporter,description);


var G__7129 = seq__7114;
var G__7130 = chunk__7115;
var G__7131 = count__7116;
var G__7132 = (i__7117 + (1));
seq__7114 = G__7129;
chunk__7115 = G__7130;
count__7116 = G__7131;
i__7117 = G__7132;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7114);
if(temp__5825__auto__){
var seq__7114__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7114__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7114__$1);
var G__7133 = cljs.core.chunk_rest.call(null,seq__7114__$1);
var G__7134 = c__5548__auto__;
var G__7135 = cljs.core.count.call(null,c__5548__auto__);
var G__7136 = (0);
seq__7114 = G__7133;
chunk__7115 = G__7134;
count__7116 = G__7135;
i__7117 = G__7136;
continue;
} else {
var reporter = cljs.core.first.call(null,seq__7114__$1);
speclj.reporting.report_description.call(null,reporter,description);


var G__7137 = cljs.core.next.call(null,seq__7114__$1);
var G__7138 = null;
var G__7139 = (0);
var G__7140 = (0);
seq__7114 = G__7137;
chunk__7115 = G__7138;
count__7116 = G__7139;
i__7117 = G__7140;
continue;
}
} else {
return null;
}
}
break;
}
});
speclj.reporting.report_runs_STAR_ = (function speclj$reporting$report_runs_STAR_(reporters,results){
var seq__7125 = cljs.core.seq.call(null,reporters);
var chunk__7126 = null;
var count__7127 = (0);
var i__7128 = (0);
while(true){
if((i__7128 < count__7127)){
var reporter = cljs.core._nth.call(null,chunk__7126,i__7128);
speclj.reporting.report_runs.call(null,reporter,results);


var G__7145 = seq__7125;
var G__7146 = chunk__7126;
var G__7147 = count__7127;
var G__7148 = (i__7128 + (1));
seq__7125 = G__7145;
chunk__7126 = G__7146;
count__7127 = G__7147;
i__7128 = G__7148;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7125);
if(temp__5825__auto__){
var seq__7125__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7125__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7125__$1);
var G__7149 = cljs.core.chunk_rest.call(null,seq__7125__$1);
var G__7150 = c__5548__auto__;
var G__7151 = cljs.core.count.call(null,c__5548__auto__);
var G__7152 = (0);
seq__7125 = G__7149;
chunk__7126 = G__7150;
count__7127 = G__7151;
i__7128 = G__7152;
continue;
} else {
var reporter = cljs.core.first.call(null,seq__7125__$1);
speclj.reporting.report_runs.call(null,reporter,results);


var G__7153 = cljs.core.next.call(null,seq__7125__$1);
var G__7154 = null;
var G__7155 = (0);
var G__7156 = (0);
seq__7125 = G__7153;
chunk__7126 = G__7154;
count__7127 = G__7155;
i__7128 = G__7156;
continue;
}
} else {
return null;
}
}
break;
}
});
speclj.reporting.report_message_STAR_ = (function speclj$reporting$report_message_STAR_(reporters,message){
var seq__7141 = cljs.core.seq.call(null,reporters);
var chunk__7142 = null;
var count__7143 = (0);
var i__7144 = (0);
while(true){
if((i__7144 < count__7143)){
var reporter = cljs.core._nth.call(null,chunk__7142,i__7144);
speclj.reporting.report_message.call(null,reporter,message);


var G__7161 = seq__7141;
var G__7162 = chunk__7142;
var G__7163 = count__7143;
var G__7164 = (i__7144 + (1));
seq__7141 = G__7161;
chunk__7142 = G__7162;
count__7143 = G__7163;
i__7144 = G__7164;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7141);
if(temp__5825__auto__){
var seq__7141__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7141__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7141__$1);
var G__7165 = cljs.core.chunk_rest.call(null,seq__7141__$1);
var G__7166 = c__5548__auto__;
var G__7167 = cljs.core.count.call(null,c__5548__auto__);
var G__7168 = (0);
seq__7141 = G__7165;
chunk__7142 = G__7166;
count__7143 = G__7167;
i__7144 = G__7168;
continue;
} else {
var reporter = cljs.core.first.call(null,seq__7141__$1);
speclj.reporting.report_message.call(null,reporter,message);


var G__7169 = cljs.core.next.call(null,seq__7141__$1);
var G__7170 = null;
var G__7171 = (0);
var G__7172 = (0);
seq__7141 = G__7169;
chunk__7142 = G__7170;
count__7143 = G__7171;
i__7144 = G__7172;
continue;
}
} else {
return null;
}
}
break;
}
});
speclj.reporting.report_error_STAR_ = (function speclj$reporting$report_error_STAR_(reporters,exception){
var seq__7157 = cljs.core.seq.call(null,reporters);
var chunk__7158 = null;
var count__7159 = (0);
var i__7160 = (0);
while(true){
if((i__7160 < count__7159)){
var reporter = cljs.core._nth.call(null,chunk__7158,i__7160);
speclj.reporting.report_error.call(null,reporter,exception);


var G__7173 = seq__7157;
var G__7174 = chunk__7158;
var G__7175 = count__7159;
var G__7176 = (i__7160 + (1));
seq__7157 = G__7173;
chunk__7158 = G__7174;
count__7159 = G__7175;
i__7160 = G__7176;
continue;
} else {
var temp__5825__auto__ = cljs.core.seq.call(null,seq__7157);
if(temp__5825__auto__){
var seq__7157__$1 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__7157__$1)){
var c__5548__auto__ = cljs.core.chunk_first.call(null,seq__7157__$1);
var G__7177 = cljs.core.chunk_rest.call(null,seq__7157__$1);
var G__7178 = c__5548__auto__;
var G__7179 = cljs.core.count.call(null,c__5548__auto__);
var G__7180 = (0);
seq__7157 = G__7177;
chunk__7158 = G__7178;
count__7159 = G__7179;
i__7160 = G__7180;
continue;
} else {
var reporter = cljs.core.first.call(null,seq__7157__$1);
speclj.reporting.report_error.call(null,reporter,exception);


var G__7181 = cljs.core.next.call(null,seq__7157__$1);
var G__7182 = null;
var G__7183 = (0);
var G__7184 = (0);
seq__7157 = G__7181;
chunk__7158 = G__7182;
count__7159 = G__7183;
i__7160 = G__7184;
continue;
}
} else {
return null;
}
}
break;
}
});

//# sourceMappingURL=reporting.js.map
