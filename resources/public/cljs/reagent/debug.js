// Compiled by ClojureScript 1.12.42 {:optimizations :none}
goog.provide('reagent.debug');
goog.require('cljs.core');
reagent.debug.has_console = (typeof console !== 'undefined');
reagent.debug.tracking = false;
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.warnings !== 'undefined')){
} else {
reagent.debug.warnings = cljs.core.atom.call(null,null);
}
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.track_console !== 'undefined')){
} else {
reagent.debug.track_console = (function (){var o = ({});
(o.warn = (function() { 
var G__6741__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"warn","warn",-436710552)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__6741 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__6742__i = 0, G__6742__a = new Array(arguments.length -  0);
while (G__6742__i < G__6742__a.length) {G__6742__a[G__6742__i] = arguments[G__6742__i + 0]; ++G__6742__i;}
  args = new cljs.core.IndexedSeq(G__6742__a,0,null);
} 
return G__6741__delegate.call(this,args);};
G__6741.cljs$lang$maxFixedArity = 0;
G__6741.cljs$lang$applyTo = (function (arglist__6743){
var args = cljs.core.seq(arglist__6743);
return G__6741__delegate(args);
});
G__6741.cljs$core$IFn$_invoke$arity$variadic = G__6741__delegate;
return G__6741;
})()
);

(o.error = (function() { 
var G__6747__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"error","error",-978969032)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__6747 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__6753__i = 0, G__6753__a = new Array(arguments.length -  0);
while (G__6753__i < G__6753__a.length) {G__6753__a[G__6753__i] = arguments[G__6753__i + 0]; ++G__6753__i;}
  args = new cljs.core.IndexedSeq(G__6753__a,0,null);
} 
return G__6747__delegate.call(this,args);};
G__6747.cljs$lang$maxFixedArity = 0;
G__6747.cljs$lang$applyTo = (function (arglist__6754){
var args = cljs.core.seq(arglist__6754);
return G__6747__delegate(args);
});
G__6747.cljs$core$IFn$_invoke$arity$variadic = G__6747__delegate;
return G__6747;
})()
);

return o;
})();
}
reagent.debug.track_warnings = (function reagent$debug$track_warnings(f){
(reagent.debug.tracking = true);

cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

f.call(null);

var warns = cljs.core.deref.call(null,reagent.debug.warnings);
cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

(reagent.debug.tracking = false);

return warns;
});

//# sourceMappingURL=debug.js.map
