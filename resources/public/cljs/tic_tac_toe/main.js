// Compiled by ClojureScript 1.12.42 {:optimizations :none}
goog.provide('tic_tac_toe.main');
goog.require('cljs.core');
goog.require('tic_tac_toe.core');
goog.require('reagent.core');
goog.require('reagent.dom');
goog.require('reagent.dom.client');
tic_tac_toe.main.hello = (function tic_tac_toe$main$hello(){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"h1","h1",-1896887462),"Goodbye from Reagent"], null);

});
tic_tac_toe.main.init = (function tic_tac_toe$main$init(){
return reagent.dom.client.render.call(null,reagent.dom.client.create_root.call(null,document.getElementById("app")),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [tic_tac_toe.main.hello], null));
});
goog.exportSymbol('tic_tac_toe.main.init', tic_tac_toe.main.init);

//# sourceMappingURL=main.js.map
