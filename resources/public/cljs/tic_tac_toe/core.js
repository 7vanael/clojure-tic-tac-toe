// Compiled by ClojureScript 1.12.42 {:optimizations :none}
goog.provide('tic_tac_toe.core');
goog.require('cljs.core');
goog.require('tic_tac_toe.board');
tic_tac_toe.core.__GT_inspect = (function tic_tac_toe$core$__GT_inspect(x){
cljs.core.prn.call(null,"x:",x);

return x;
});
tic_tac_toe.core.initial_state = (function tic_tac_toe$core$initial_state(state){
return cljs.core.merge.call(null,new cljs.core.PersistentArrayMap(null, 6, [new cljs.core.Keyword(null,"interface","interface",394869923),new cljs.core.Keyword(null,"tui","tui",-1217057309),new cljs.core.Keyword(null,"board","board",-1907017633),null,new cljs.core.Keyword(null,"active-player-index","active-player-index",-93298655),(0),new cljs.core.Keyword(null,"status","status",-1997798413),new cljs.core.Keyword(null,"welcome","welcome",-578152123),new cljs.core.Keyword(null,"players","players",-1361554569),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"character","character",380652989),"X",new cljs.core.Keyword(null,"play-type","play-type",519061088),null,new cljs.core.Keyword(null,"difficulty","difficulty",755680807),null], null),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"character","character",380652989),"O",new cljs.core.Keyword(null,"play-type","play-type",519061088),null,new cljs.core.Keyword(null,"difficulty","difficulty",755680807),null], null)], null),new cljs.core.Keyword(null,"save","save",1850079149),new cljs.core.Keyword(null,"sql","sql",1251448786)], null),state);
});
tic_tac_toe.core.player_options = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"human","human",-772334390),new cljs.core.Keyword(null,"computer","computer",-1035300443)], null);
tic_tac_toe.core.difficulty_options = new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"easy","easy",315769928),new cljs.core.Keyword(null,"medium","medium",-1864319384),new cljs.core.Keyword(null,"hard","hard",2068420191)], null);
tic_tac_toe.core.board_options = new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"3x3","3x3",570362544),(3),new cljs.core.Keyword(null,"4x4","4x4",121507723),(4),new cljs.core.Keyword(null,"3x3x3","3x3x3",1381331540),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(3),(3),(3)], null)], null);
tic_tac_toe.core.dual_dispatch = (function tic_tac_toe$core$dual_dispatch(state){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"interface","interface",394869923).cljs$core$IFn$_invoke$arity$1(state),new cljs.core.Keyword(null,"status","status",-1997798413).cljs$core$IFn$_invoke$arity$1(state)], null);
});
tic_tac_toe.core.get_computer_difficulty = (function tic_tac_toe$core$get_computer_difficulty(p__7343){
var map__7344 = p__7343;
var map__7344__$1 = cljs.core.__destructure_map.call(null,map__7344);
var active_player_index = cljs.core.get.call(null,map__7344__$1,new cljs.core.Keyword(null,"active-player-index","active-player-index",-93298655));
var players = cljs.core.get.call(null,map__7344__$1,new cljs.core.Keyword(null,"players","players",-1361554569));
return cljs.core.get_in.call(null,players,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [active_player_index,new cljs.core.Keyword(null,"difficulty","difficulty",755680807)], null));
});
tic_tac_toe.core.active_player_type = (function tic_tac_toe$core$active_player_type(p__7357){
var map__7358 = p__7357;
var map__7358__$1 = cljs.core.__destructure_map.call(null,map__7358);
var players = cljs.core.get.call(null,map__7358__$1,new cljs.core.Keyword(null,"players","players",-1361554569));
var active_player_index = cljs.core.get.call(null,map__7358__$1,new cljs.core.Keyword(null,"active-player-index","active-player-index",-93298655));
return cljs.core.get_in.call(null,players,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [active_player_index,new cljs.core.Keyword(null,"play-type","play-type",519061088)], null));
});
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.start_game !== 'undefined')){
} else {
tic_tac_toe.core.start_game = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","start-game"),new cljs.core.Keyword(null,"interface","interface",394869923),new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.take_computer_turn !== 'undefined')){
} else {
tic_tac_toe.core.take_computer_turn = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","take-computer-turn"),tic_tac_toe.core.get_computer_difficulty,new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.take_human_turn !== 'undefined')){
} else {
tic_tac_toe.core.take_human_turn = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","take-human-turn"),new cljs.core.Keyword(null,"interface","interface",394869923),new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.save_game !== 'undefined')){
} else {
tic_tac_toe.core.save_game = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","save-game"),new cljs.core.Keyword(null,"save","save",1850079149),new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.load_game !== 'undefined')){
} else {
tic_tac_toe.core.load_game = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","load-game"),new cljs.core.Keyword(null,"save","save",1850079149),new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.delete_save !== 'undefined')){
} else {
tic_tac_toe.core.delete_save = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","delete-save"),new cljs.core.Keyword(null,"save","save",1850079149),new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.draw_state !== 'undefined')){
} else {
tic_tac_toe.core.draw_state = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","draw-state"),tic_tac_toe.core.dual_dispatch,new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.mouse_clicked !== 'undefined')){
} else {
tic_tac_toe.core.mouse_clicked = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","mouse-clicked"),(function() { 
var G__7418__delegate = function (state,_){
return new cljs.core.Keyword(null,"status","status",-1997798413).cljs$core$IFn$_invoke$arity$1(state);
};
var G__7418 = function (state,var_args){
var _ = null;
if (arguments.length > 1) {
var G__7419__i = 0, G__7419__a = new Array(arguments.length -  1);
while (G__7419__i < G__7419__a.length) {G__7419__a[G__7419__i] = arguments[G__7419__i + 1]; ++G__7419__i;}
  _ = new cljs.core.IndexedSeq(G__7419__a,0,null);
} 
return G__7418__delegate.call(this,state,_);};
G__7418.cljs$lang$maxFixedArity = 1;
G__7418.cljs$lang$applyTo = (function (arglist__7420){
var state = cljs.core.first(arglist__7420);
var _ = cljs.core.rest(arglist__7420);
return G__7418__delegate(state,_);
});
G__7418.cljs$core$IFn$_invoke$arity$variadic = G__7418__delegate;
return G__7418;
})()
,new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
if((typeof tic_tac_toe !== 'undefined') && (typeof tic_tac_toe.core !== 'undefined') && (typeof tic_tac_toe.core.get_selection !== 'undefined')){
} else {
tic_tac_toe.core.get_selection = (function (){var method_table__5622__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var prefer_table__5623__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var method_cache__5624__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var cached_hierarchy__5625__auto__ = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
var hierarchy__5626__auto__ = cljs.core.get.call(null,cljs.core.PersistentArrayMap.EMPTY,new cljs.core.Keyword(null,"hierarchy","hierarchy",-1053470341),cljs.core.get_global_hierarchy.call(null));
return (new cljs.core.MultiFn(cljs.core.symbol.call(null,"tic-tac-toe.core","get-selection"),tic_tac_toe.core.dual_dispatch,new cljs.core.Keyword(null,"default","default",-1987822328),hierarchy__5626__auto__,method_table__5622__auto__,prefer_table__5623__auto__,method_cache__5624__auto__,cached_hierarchy__5625__auto__));
})();
}
tic_tac_toe.core.do_take_human_turn = (function tic_tac_toe$core$do_take_human_turn(p__7422){
var map__7423 = p__7422;
var map__7423__$1 = cljs.core.__destructure_map.call(null,map__7423);
var state = map__7423__$1;
var board = cljs.core.get.call(null,map__7423__$1,new cljs.core.Keyword(null,"board","board",-1907017633));
var players = cljs.core.get.call(null,map__7423__$1,new cljs.core.Keyword(null,"players","players",-1361554569));
var active_player_index = cljs.core.get.call(null,map__7423__$1,new cljs.core.Keyword(null,"active-player-index","active-player-index",-93298655));
var next_play = new cljs.core.Keyword(null,"response","response",-1068424192).cljs$core$IFn$_invoke$arity$1(state);
var clean_state = cljs.core.dissoc.call(null,state,new cljs.core.Keyword(null,"response","response",-1068424192));
return cljs.core.assoc.call(null,clean_state,new cljs.core.Keyword(null,"board","board",-1907017633),tic_tac_toe.board.take_square.call(null,board,tic_tac_toe.board.space__GT_coordinates.call(null,next_play,board),cljs.core.get_in.call(null,players,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [active_player_index,new cljs.core.Keyword(null,"character","character",380652989)], null))));
});
tic_tac_toe.core.states_to_break_loop = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"tie","tie",-487201694),null,new cljs.core.Keyword(null,"winner","winner",714604679),null], null), null);
tic_tac_toe.core.game_over_QMARK_ = (function tic_tac_toe$core$game_over_QMARK_(p__7431){
var map__7432 = p__7431;
var map__7432__$1 = cljs.core.__destructure_map.call(null,map__7432);
var status = cljs.core.get.call(null,map__7432__$1,new cljs.core.Keyword(null,"status","status",-1997798413));
return tic_tac_toe.core.states_to_break_loop.call(null,status);
});
tic_tac_toe.core.player_played_QMARK_ = (function tic_tac_toe$core$player_played_QMARK_(p__7441){
var map__7442 = p__7441;
var map__7442__$1 = cljs.core.__destructure_map.call(null,map__7442);
var active_player_index = cljs.core.get.call(null,map__7442__$1,new cljs.core.Keyword(null,"active-player-index","active-player-index",-93298655));
var board = cljs.core.get.call(null,map__7442__$1,new cljs.core.Keyword(null,"board","board",-1907017633));
var players = cljs.core.get.call(null,map__7442__$1,new cljs.core.Keyword(null,"players","players",-1361554569));
var current_char = cljs.core.get_in.call(null,players,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [active_player_index,new cljs.core.Keyword(null,"character","character",380652989)], null));
return (!(cljs.core._EQ_.call(null,current_char,tic_tac_toe.board.next_player.call(null,board))));
});
tic_tac_toe.core.maybe_take_turn = (function tic_tac_toe$core$maybe_take_turn(state){
if(tic_tac_toe.core.player_played_QMARK_.call(null,state)){
return cljs.core.dissoc.call(null,state,new cljs.core.Keyword(null,"response","response",-1068424192));
} else {
return tic_tac_toe.core.do_take_human_turn.call(null,state);
}
});
tic_tac_toe.core.currently_human_QMARK_ = (function tic_tac_toe$core$currently_human_QMARK_(state){
return cljs.core._EQ_.call(null,new cljs.core.Keyword(null,"human","human",-772334390),tic_tac_toe.core.active_player_type.call(null,state));
});
tic_tac_toe.core.take_turn = (function tic_tac_toe$core$take_turn(state){
if(tic_tac_toe.core.currently_human_QMARK_.call(null,state)){
return tic_tac_toe.core.take_human_turn.call(null,state);
} else {
return tic_tac_toe.core.take_computer_turn.call(null,state);
}
});
tic_tac_toe.core.change_player = (function tic_tac_toe$core$change_player(p__7453){
var map__7454 = p__7453;
var map__7454__$1 = cljs.core.__destructure_map.call(null,map__7454);
var state = map__7454__$1;
var active_player_index = cljs.core.get.call(null,map__7454__$1,new cljs.core.Keyword(null,"active-player-index","active-player-index",-93298655));
if(cljs.core.truth_((function (){var or__5025__auto__ = (!(tic_tac_toe.core.player_played_QMARK_.call(null,state)));
if(or__5025__auto__){
return or__5025__auto__;
} else {
return tic_tac_toe.core.game_over_QMARK_.call(null,state);
}
})())){
return state;
} else {
return cljs.core.assoc.call(null,state,new cljs.core.Keyword(null,"active-player-index","active-player-index",-93298655),((cljs.core._EQ_.call(null,active_player_index,(0)))?(1):(0)));
}
});
tic_tac_toe.core.play_turn_BANG_ = (function tic_tac_toe$core$play_turn_BANG_(state){
return tic_tac_toe.core.save_game.call(null,tic_tac_toe.core.change_player.call(null,tic_tac_toe.board.evaluate_board.call(null,tic_tac_toe.core.take_turn.call(null,state))));
});
tic_tac_toe.core.fresh_start = (function tic_tac_toe$core$fresh_start(p__7459){
var map__7460 = p__7459;
var map__7460__$1 = cljs.core.__destructure_map.call(null,map__7460);
var interface$ = cljs.core.get.call(null,map__7460__$1,new cljs.core.Keyword(null,"interface","interface",394869923));
var save = cljs.core.get.call(null,map__7460__$1,new cljs.core.Keyword(null,"save","save",1850079149));
return tic_tac_toe.core.initial_state.call(null,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"interface","interface",394869923),interface$,new cljs.core.Keyword(null,"save","save",1850079149),save,new cljs.core.Keyword(null,"status","status",-1997798413),new cljs.core.Keyword(null,"config-x-type","config-x-type",-785668220)], null));
});
tic_tac_toe.core.go_in_progress = (function tic_tac_toe$core$go_in_progress(state){
return cljs.core.assoc.call(null,state,new cljs.core.Keyword(null,"status","status",-1997798413),new cljs.core.Keyword(null,"in-progress","in-progress",2126442630));
});
tic_tac_toe.core.maybe_load_save = (function tic_tac_toe$core$maybe_load_save(state){
var saved_game = tic_tac_toe.core.load_game.call(null,state);
if(cljs.core._EQ_.call(null,new cljs.core.Keyword(null,"found-save","found-save",-1464827304),new cljs.core.Keyword(null,"status","status",-1997798413).cljs$core$IFn$_invoke$arity$1(saved_game))){
return cljs.core.assoc.call(null,saved_game,new cljs.core.Keyword(null,"interface","interface",394869923),new cljs.core.Keyword(null,"interface","interface",394869923).cljs$core$IFn$_invoke$arity$1(state));
} else {
return cljs.core.assoc.call(null,tic_tac_toe.core.initial_state.call(null,state),new cljs.core.Keyword(null,"status","status",-1997798413),new cljs.core.Keyword(null,"config-x-type","config-x-type",-785668220));
}
});
tic_tac_toe.core.maybe_resume_save = (function tic_tac_toe$core$maybe_resume_save(state,resume){
if(cljs.core.truth_(resume)){
return tic_tac_toe.core.go_in_progress.call(null,state);
} else {
return tic_tac_toe.core.fresh_start.call(null,state);
}
});
tic_tac_toe.core.config_x_type = (function tic_tac_toe$core$config_x_type(state){
var play_type = new cljs.core.Keyword(null,"response","response",-1068424192).cljs$core$IFn$_invoke$arity$1(state);
var next_status = ((cljs.core._EQ_.call(null,play_type,new cljs.core.Keyword(null,"human","human",-772334390)))?new cljs.core.Keyword(null,"config-o-type","config-o-type",127599360):new cljs.core.Keyword(null,"config-x-difficulty","config-x-difficulty",-598657456));
var new_state = cljs.core.assoc_in.call(null,state,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"players","players",-1361554569),(0),new cljs.core.Keyword(null,"play-type","play-type",519061088)], null),play_type);
return cljs.core.dissoc.call(null,cljs.core.assoc.call(null,new_state,new cljs.core.Keyword(null,"status","status",-1997798413),next_status),new cljs.core.Keyword(null,"response","response",-1068424192));
});
tic_tac_toe.core.config_x_difficulty = (function tic_tac_toe$core$config_x_difficulty(state){
var difficulty = new cljs.core.Keyword(null,"response","response",-1068424192).cljs$core$IFn$_invoke$arity$1(state);
var next_status = new cljs.core.Keyword(null,"config-o-type","config-o-type",127599360);
var new_state = cljs.core.assoc_in.call(null,state,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"players","players",-1361554569),(0),new cljs.core.Keyword(null,"difficulty","difficulty",755680807)], null),difficulty);
return cljs.core.dissoc.call(null,cljs.core.assoc.call(null,new_state,new cljs.core.Keyword(null,"status","status",-1997798413),next_status),new cljs.core.Keyword(null,"response","response",-1068424192));
});
tic_tac_toe.core.config_o_type = (function tic_tac_toe$core$config_o_type(state){
var play_type = new cljs.core.Keyword(null,"response","response",-1068424192).cljs$core$IFn$_invoke$arity$1(state);
var next_status = ((cljs.core._EQ_.call(null,play_type,new cljs.core.Keyword(null,"human","human",-772334390)))?new cljs.core.Keyword(null,"select-board","select-board",-279755643):new cljs.core.Keyword(null,"config-o-difficulty","config-o-difficulty",-517527095));
var new_state = cljs.core.assoc_in.call(null,state,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"players","players",-1361554569),(1),new cljs.core.Keyword(null,"play-type","play-type",519061088)], null),play_type);
return cljs.core.dissoc.call(null,cljs.core.assoc.call(null,new_state,new cljs.core.Keyword(null,"status","status",-1997798413),next_status),new cljs.core.Keyword(null,"response","response",-1068424192));
});
tic_tac_toe.core.config_o_difficulty = (function tic_tac_toe$core$config_o_difficulty(state){
var difficulty = new cljs.core.Keyword(null,"response","response",-1068424192).cljs$core$IFn$_invoke$arity$1(state);
var next_status = new cljs.core.Keyword(null,"select-board","select-board",-279755643);
var new_state = cljs.core.assoc_in.call(null,state,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"players","players",-1361554569),(1),new cljs.core.Keyword(null,"difficulty","difficulty",755680807)], null),difficulty);
return cljs.core.dissoc.call(null,cljs.core.assoc.call(null,new_state,new cljs.core.Keyword(null,"status","status",-1997798413),next_status),new cljs.core.Keyword(null,"response","response",-1068424192));
});
tic_tac_toe.core.select_board = (function tic_tac_toe$core$select_board(state){
var board_size = new cljs.core.Keyword(null,"response","response",-1068424192).cljs$core$IFn$_invoke$arity$1(state);
var next_status = new cljs.core.Keyword(null,"in-progress","in-progress",2126442630);
var new_state = cljs.core.assoc.call(null,state,new cljs.core.Keyword(null,"board","board",-1907017633),tic_tac_toe.board.new_board.call(null,board_size));
return cljs.core.dissoc.call(null,cljs.core.assoc.call(null,new_state,new cljs.core.Keyword(null,"status","status",-1997798413),next_status),new cljs.core.Keyword(null,"response","response",-1068424192));
});
tic_tac_toe.core.maybe_play_again = (function tic_tac_toe$core$maybe_play_again(state){
tic_tac_toe.core.delete_save.call(null,state);

if(cljs.core.truth_(new cljs.core.Keyword(null,"response","response",-1068424192).cljs$core$IFn$_invoke$arity$1(state))){
return cljs.core.assoc.call(null,tic_tac_toe.core.initial_state.call(null,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"interface","interface",394869923),new cljs.core.Keyword(null,"interface","interface",394869923).cljs$core$IFn$_invoke$arity$1(state),new cljs.core.Keyword(null,"save","save",1850079149),new cljs.core.Keyword(null,"save","save",1850079149).cljs$core$IFn$_invoke$arity$1(state)], null)),new cljs.core.Keyword(null,"status","status",-1997798413),new cljs.core.Keyword(null,"config-x-type","config-x-type",-785668220));
} else {
return cljs.core.dissoc.call(null,cljs.core.assoc.call(null,state,new cljs.core.Keyword(null,"status","status",-1997798413),new cljs.core.Keyword(null,"game-over","game-over",-607322695)),new cljs.core.Keyword(null,"response","response",-1068424192));
}
});

//# sourceMappingURL=core.js.map
