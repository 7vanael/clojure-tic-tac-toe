// Compiled by ClojureScript 1.12.42 {:optimizations :none}
goog.provide('tic_tac_toe.board');
goog.require('cljs.core');
tic_tac_toe.board.new_2d_board = (function tic_tac_toe$board$new_2d_board(size){
return cljs.core.mapv.call(null,cljs.core.vec,cljs.core.partition.call(null,size,cljs.core.range.call(null,(1),((size * size) + (1)))));
});
tic_tac_toe.board.new_3d_board = (function tic_tac_toe$board$new_3d_board(size){
return cljs.core.vec.call(null,(function (){var iter__5503__auto__ = (function tic_tac_toe$board$new_3d_board_$_iter__6932(s__6933){
return (new cljs.core.LazySeq(null,(function (){
var s__6933__$1 = s__6933;
while(true){
var temp__5825__auto__ = cljs.core.seq.call(null,s__6933__$1);
if(temp__5825__auto__){
var s__6933__$2 = temp__5825__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,s__6933__$2)){
var c__5501__auto__ = cljs.core.chunk_first.call(null,s__6933__$2);
var size__5502__auto__ = cljs.core.count.call(null,c__5501__auto__);
var b__6935 = cljs.core.chunk_buffer.call(null,size__5502__auto__);
if((function (){var i__6934 = (0);
while(true){
if((i__6934 < size__5502__auto__)){
var z = cljs.core._nth.call(null,c__5501__auto__,i__6934);
cljs.core.chunk_append.call(null,b__6935,cljs.core.vec.call(null,(function (){var iter__5503__auto__ = ((function (i__6934,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__){
return (function tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936(s__6937){
return (new cljs.core.LazySeq(null,((function (i__6934,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__){
return (function (){
var s__6937__$1 = s__6937;
while(true){
var temp__5825__auto____$1 = cljs.core.seq.call(null,s__6937__$1);
if(temp__5825__auto____$1){
var s__6937__$2 = temp__5825__auto____$1;
if(cljs.core.chunked_seq_QMARK_.call(null,s__6937__$2)){
var c__5501__auto____$1 = cljs.core.chunk_first.call(null,s__6937__$2);
var size__5502__auto____$1 = cljs.core.count.call(null,c__5501__auto____$1);
var b__6939 = cljs.core.chunk_buffer.call(null,size__5502__auto____$1);
if((function (){var i__6938 = (0);
while(true){
if((i__6938 < size__5502__auto____$1)){
var x = cljs.core._nth.call(null,c__5501__auto____$1,i__6938);
cljs.core.chunk_append.call(null,b__6939,cljs.core.vec.call(null,(function (){var iter__5503__auto__ = ((function (i__6938,i__6934,x,c__5501__auto____$1,size__5502__auto____$1,b__6939,s__6937__$2,temp__5825__auto____$1,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__){
return (function tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936_$_iter__6940(s__6941){
return (new cljs.core.LazySeq(null,((function (i__6938,i__6934,x,c__5501__auto____$1,size__5502__auto____$1,b__6939,s__6937__$2,temp__5825__auto____$1,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__){
return (function (){
var s__6941__$1 = s__6941;
while(true){
var temp__5825__auto____$2 = cljs.core.seq.call(null,s__6941__$1);
if(temp__5825__auto____$2){
var s__6941__$2 = temp__5825__auto____$2;
if(cljs.core.chunked_seq_QMARK_.call(null,s__6941__$2)){
var c__5501__auto____$2 = cljs.core.chunk_first.call(null,s__6941__$2);
var size__5502__auto____$2 = cljs.core.count.call(null,c__5501__auto____$2);
var b__6943 = cljs.core.chunk_buffer.call(null,size__5502__auto____$2);
if((function (){var i__6942 = (0);
while(true){
if((i__6942 < size__5502__auto____$2)){
var y = cljs.core._nth.call(null,c__5501__auto____$2,i__6942);
cljs.core.chunk_append.call(null,b__6943,((((1) + y) + (size * x)) + ((size * size) * z)));

var G__6978 = (i__6942 + (1));
i__6942 = G__6978;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6943),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936_$_iter__6940.call(null,cljs.core.chunk_rest.call(null,s__6941__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6943),null);
}
} else {
var y = cljs.core.first.call(null,s__6941__$2);
return cljs.core.cons.call(null,((((1) + y) + (size * x)) + ((size * size) * z)),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936_$_iter__6940.call(null,cljs.core.rest.call(null,s__6941__$2)));
}
} else {
return null;
}
break;
}
});})(i__6938,i__6934,x,c__5501__auto____$1,size__5502__auto____$1,b__6939,s__6937__$2,temp__5825__auto____$1,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__))
,null,null));
});})(i__6938,i__6934,x,c__5501__auto____$1,size__5502__auto____$1,b__6939,s__6937__$2,temp__5825__auto____$1,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__))
;
return iter__5503__auto__.call(null,cljs.core.range.call(null,size));
})()));

var G__6980 = (i__6938 + (1));
i__6938 = G__6980;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6939),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936.call(null,cljs.core.chunk_rest.call(null,s__6937__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6939),null);
}
} else {
var x = cljs.core.first.call(null,s__6937__$2);
return cljs.core.cons.call(null,cljs.core.vec.call(null,(function (){var iter__5503__auto__ = ((function (i__6934,x,s__6937__$2,temp__5825__auto____$1,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__){
return (function tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936_$_iter__6948(s__6949){
return (new cljs.core.LazySeq(null,((function (i__6934,x,s__6937__$2,temp__5825__auto____$1,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__){
return (function (){
var s__6949__$1 = s__6949;
while(true){
var temp__5825__auto____$2 = cljs.core.seq.call(null,s__6949__$1);
if(temp__5825__auto____$2){
var s__6949__$2 = temp__5825__auto____$2;
if(cljs.core.chunked_seq_QMARK_.call(null,s__6949__$2)){
var c__5501__auto____$1 = cljs.core.chunk_first.call(null,s__6949__$2);
var size__5502__auto____$1 = cljs.core.count.call(null,c__5501__auto____$1);
var b__6951 = cljs.core.chunk_buffer.call(null,size__5502__auto____$1);
if((function (){var i__6950 = (0);
while(true){
if((i__6950 < size__5502__auto____$1)){
var y = cljs.core._nth.call(null,c__5501__auto____$1,i__6950);
cljs.core.chunk_append.call(null,b__6951,((((1) + y) + (size * x)) + ((size * size) * z)));

var G__6990 = (i__6950 + (1));
i__6950 = G__6990;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6951),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936_$_iter__6948.call(null,cljs.core.chunk_rest.call(null,s__6949__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6951),null);
}
} else {
var y = cljs.core.first.call(null,s__6949__$2);
return cljs.core.cons.call(null,((((1) + y) + (size * x)) + ((size * size) * z)),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936_$_iter__6948.call(null,cljs.core.rest.call(null,s__6949__$2)));
}
} else {
return null;
}
break;
}
});})(i__6934,x,s__6937__$2,temp__5825__auto____$1,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__))
,null,null));
});})(i__6934,x,s__6937__$2,temp__5825__auto____$1,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__))
;
return iter__5503__auto__.call(null,cljs.core.range.call(null,size));
})()),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6936.call(null,cljs.core.rest.call(null,s__6937__$2)));
}
} else {
return null;
}
break;
}
});})(i__6934,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__))
,null,null));
});})(i__6934,z,c__5501__auto__,size__5502__auto__,b__6935,s__6933__$2,temp__5825__auto__))
;
return iter__5503__auto__.call(null,cljs.core.range.call(null,size));
})()));

var G__6998 = (i__6934 + (1));
i__6934 = G__6998;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6935),tic_tac_toe$board$new_3d_board_$_iter__6932.call(null,cljs.core.chunk_rest.call(null,s__6933__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6935),null);
}
} else {
var z = cljs.core.first.call(null,s__6933__$2);
return cljs.core.cons.call(null,cljs.core.vec.call(null,(function (){var iter__5503__auto__ = ((function (z,s__6933__$2,temp__5825__auto__){
return (function tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962(s__6963){
return (new cljs.core.LazySeq(null,(function (){
var s__6963__$1 = s__6963;
while(true){
var temp__5825__auto____$1 = cljs.core.seq.call(null,s__6963__$1);
if(temp__5825__auto____$1){
var s__6963__$2 = temp__5825__auto____$1;
if(cljs.core.chunked_seq_QMARK_.call(null,s__6963__$2)){
var c__5501__auto__ = cljs.core.chunk_first.call(null,s__6963__$2);
var size__5502__auto__ = cljs.core.count.call(null,c__5501__auto__);
var b__6965 = cljs.core.chunk_buffer.call(null,size__5502__auto__);
if((function (){var i__6964 = (0);
while(true){
if((i__6964 < size__5502__auto__)){
var x = cljs.core._nth.call(null,c__5501__auto__,i__6964);
cljs.core.chunk_append.call(null,b__6965,cljs.core.vec.call(null,(function (){var iter__5503__auto__ = ((function (i__6964,x,c__5501__auto__,size__5502__auto__,b__6965,s__6963__$2,temp__5825__auto____$1,z,s__6933__$2,temp__5825__auto__){
return (function tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962_$_iter__6966(s__6967){
return (new cljs.core.LazySeq(null,((function (i__6964,x,c__5501__auto__,size__5502__auto__,b__6965,s__6963__$2,temp__5825__auto____$1,z,s__6933__$2,temp__5825__auto__){
return (function (){
var s__6967__$1 = s__6967;
while(true){
var temp__5825__auto____$2 = cljs.core.seq.call(null,s__6967__$1);
if(temp__5825__auto____$2){
var s__6967__$2 = temp__5825__auto____$2;
if(cljs.core.chunked_seq_QMARK_.call(null,s__6967__$2)){
var c__5501__auto____$1 = cljs.core.chunk_first.call(null,s__6967__$2);
var size__5502__auto____$1 = cljs.core.count.call(null,c__5501__auto____$1);
var b__6969 = cljs.core.chunk_buffer.call(null,size__5502__auto____$1);
if((function (){var i__6968 = (0);
while(true){
if((i__6968 < size__5502__auto____$1)){
var y = cljs.core._nth.call(null,c__5501__auto____$1,i__6968);
cljs.core.chunk_append.call(null,b__6969,((((1) + y) + (size * x)) + ((size * size) * z)));

var G__7011 = (i__6968 + (1));
i__6968 = G__7011;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6969),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962_$_iter__6966.call(null,cljs.core.chunk_rest.call(null,s__6967__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6969),null);
}
} else {
var y = cljs.core.first.call(null,s__6967__$2);
return cljs.core.cons.call(null,((((1) + y) + (size * x)) + ((size * size) * z)),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962_$_iter__6966.call(null,cljs.core.rest.call(null,s__6967__$2)));
}
} else {
return null;
}
break;
}
});})(i__6964,x,c__5501__auto__,size__5502__auto__,b__6965,s__6963__$2,temp__5825__auto____$1,z,s__6933__$2,temp__5825__auto__))
,null,null));
});})(i__6964,x,c__5501__auto__,size__5502__auto__,b__6965,s__6963__$2,temp__5825__auto____$1,z,s__6933__$2,temp__5825__auto__))
;
return iter__5503__auto__.call(null,cljs.core.range.call(null,size));
})()));

var G__7012 = (i__6964 + (1));
i__6964 = G__7012;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6965),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962.call(null,cljs.core.chunk_rest.call(null,s__6963__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6965),null);
}
} else {
var x = cljs.core.first.call(null,s__6963__$2);
return cljs.core.cons.call(null,cljs.core.vec.call(null,(function (){var iter__5503__auto__ = ((function (x,s__6963__$2,temp__5825__auto____$1,z,s__6933__$2,temp__5825__auto__){
return (function tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962_$_iter__6972(s__6973){
return (new cljs.core.LazySeq(null,(function (){
var s__6973__$1 = s__6973;
while(true){
var temp__5825__auto____$2 = cljs.core.seq.call(null,s__6973__$1);
if(temp__5825__auto____$2){
var s__6973__$2 = temp__5825__auto____$2;
if(cljs.core.chunked_seq_QMARK_.call(null,s__6973__$2)){
var c__5501__auto__ = cljs.core.chunk_first.call(null,s__6973__$2);
var size__5502__auto__ = cljs.core.count.call(null,c__5501__auto__);
var b__6975 = cljs.core.chunk_buffer.call(null,size__5502__auto__);
if((function (){var i__6974 = (0);
while(true){
if((i__6974 < size__5502__auto__)){
var y = cljs.core._nth.call(null,c__5501__auto__,i__6974);
cljs.core.chunk_append.call(null,b__6975,((((1) + y) + (size * x)) + ((size * size) * z)));

var G__7020 = (i__6974 + (1));
i__6974 = G__7020;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6975),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962_$_iter__6972.call(null,cljs.core.chunk_rest.call(null,s__6973__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__6975),null);
}
} else {
var y = cljs.core.first.call(null,s__6973__$2);
return cljs.core.cons.call(null,((((1) + y) + (size * x)) + ((size * size) * z)),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962_$_iter__6972.call(null,cljs.core.rest.call(null,s__6973__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});})(x,s__6963__$2,temp__5825__auto____$1,z,s__6933__$2,temp__5825__auto__))
;
return iter__5503__auto__.call(null,cljs.core.range.call(null,size));
})()),tic_tac_toe$board$new_3d_board_$_iter__6932_$_iter__6962.call(null,cljs.core.rest.call(null,s__6963__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});})(z,s__6933__$2,temp__5825__auto__))
;
return iter__5503__auto__.call(null,cljs.core.range.call(null,size));
})()),tic_tac_toe$board$new_3d_board_$_iter__6932.call(null,cljs.core.rest.call(null,s__6933__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5503__auto__.call(null,cljs.core.range.call(null,size));
})());
});
tic_tac_toe.board.get_size_complexity = (function tic_tac_toe$board$get_size_complexity(size){
if(cljs.core.sequential_QMARK_.call(null,size)){
return cljs.core.count.call(null,size);
} else {
return new cljs.core.Keyword(null,"single-digit","single-digit",1119604737);
}
});
tic_tac_toe.board.new_board = (function tic_tac_toe$board$new_board(size){
if(cljs.core._EQ_.call(null,new cljs.core.Keyword(null,"single-digit","single-digit",1119604737),tic_tac_toe.board.get_size_complexity.call(null,size))){
return tic_tac_toe.board.new_2d_board.call(null,size);
} else {
return tic_tac_toe.board.new_3d_board.call(null,cljs.core.first.call(null,size));
}
});
tic_tac_toe.board.available_QMARK_ = (function tic_tac_toe$board$available_QMARK_(board,coordinates){
return typeof cljs.core.get_in.call(null,board,coordinates) === 'number';
});
tic_tac_toe.board.play_options = (function tic_tac_toe$board$play_options(board){
return cljs.core.filter.call(null,cljs.core.number_QMARK_,cljs.core.flatten.call(null,board));
});
tic_tac_toe.board.next_player = (function tic_tac_toe$board$next_player(board){
var flat_board = cljs.core.flatten.call(null,board);
var played = cljs.core.count.call(null,cljs.core.filter.call(null,cljs.core.string_QMARK_,flat_board));
if(cljs.core.even_QMARK_.call(null,played)){
return "X";
} else {
return "O";
}
});
tic_tac_toe.board.space__GT_2d_coordinates = (function tic_tac_toe$board$space__GT_2d_coordinates(number,board){
var width = cljs.core.count.call(null,board);
var x = cljs.core.quot.call(null,(number - (1)),width);
var y = cljs.core.rem.call(null,(number - (1)),width);
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [x,y], null);
});
tic_tac_toe.board.space__GT_3d_coordinates = (function tic_tac_toe$board$space__GT_3d_coordinates(number,board){
var single_dimension = cljs.core.count.call(null,board);
var single_slice = (single_dimension * single_dimension);
var z = cljs.core.quot.call(null,(number - (1)),single_slice);
var one_board = cljs.core.rem.call(null,(number - (1)),single_slice);
var x = cljs.core.quot.call(null,one_board,single_dimension);
var y = cljs.core.rem.call(null,one_board,single_dimension);
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [z,x,y], null);
});
tic_tac_toe.board.board_3d_QMARK_ = (function tic_tac_toe$board$board_3d_QMARK_(board){
return cljs.core.vector_QMARK_.call(null,cljs.core.first.call(null,cljs.core.first.call(null,board)));
});
tic_tac_toe.board.get_board_complexity = (function tic_tac_toe$board$get_board_complexity(board){
if(tic_tac_toe.board.board_3d_QMARK_.call(null,board)){
return (3);
} else {
return (2);
}
});
tic_tac_toe.board.space__GT_coordinates = (function tic_tac_toe$board$space__GT_coordinates(number,board){
if(cljs.core._EQ_.call(null,(2),tic_tac_toe.board.get_board_complexity.call(null,board))){
return tic_tac_toe.board.space__GT_2d_coordinates.call(null,number,board);
} else {
return tic_tac_toe.board.space__GT_3d_coordinates.call(null,number,board);
}
});
tic_tac_toe.board.take_square = (function tic_tac_toe$board$take_square(board,coordinates,character){
if(tic_tac_toe.board.available_QMARK_.call(null,board,coordinates)){
return cljs.core.assoc_in.call(null,board,coordinates,character);
} else {
return board;
}
});
tic_tac_toe.board.any_space_available_QMARK_ = (function tic_tac_toe$board$any_space_available_QMARK_(board){
return cljs.core.some.call(null,cljs.core.number_QMARK_,cljs.core.flatten.call(null,board));
});
tic_tac_toe.board.all_matching_QMARK_ = (function tic_tac_toe$board$all_matching_QMARK_(collection,character){
return cljs.core.every_QMARK_.call(null,(function (p1__6989_SHARP_){
return cljs.core._EQ_.call(null,p1__6989_SHARP_,character);
}),collection);
});
tic_tac_toe.board.win_row_QMARK_ = (function tic_tac_toe$board$win_row_QMARK_(board,character){
return cljs.core.some.call(null,(function (p1__6992_SHARP_){
return tic_tac_toe.board.all_matching_QMARK_.call(null,p1__6992_SHARP_,character);
}),board);
});
tic_tac_toe.board.win_column_QMARK_ = (function tic_tac_toe$board$win_column_QMARK_(board,character){
return tic_tac_toe.board.win_row_QMARK_.call(null,cljs.core.apply.call(null,cljs.core.mapv,cljs.core.vector,board),character);
});
tic_tac_toe.board.next_location = (function tic_tac_toe$board$next_location(location,step){
return cljs.core.mapv.call(null,cljs.core._PLUS_,location,step);
});
tic_tac_toe.board.win_diag_QMARK_ = (function tic_tac_toe$board$win_diag_QMARK_(board,character){
var diag = cljs.core.take.call(null,cljs.core.count.call(null,board),cljs.core.iterate.call(null,cljs.core.partial.call(null,tic_tac_toe.board.next_location,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(1)], null)),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(0)], null)));
var ortho_diag = cljs.core.take.call(null,cljs.core.count.call(null,board),cljs.core.iterate.call(null,cljs.core.partial.call(null,tic_tac_toe.board.next_location,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(-1)], null)),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(cljs.core.count.call(null,board) - (1))], null)));
return ((cljs.core.every_QMARK_.call(null,(function (p1__6996_SHARP_){
return cljs.core._EQ_.call(null,character,cljs.core.get_in.call(null,board,p1__6996_SHARP_));
}),diag)) || (cljs.core.every_QMARK_.call(null,(function (p1__6997_SHARP_){
return cljs.core._EQ_.call(null,character,cljs.core.get_in.call(null,board,p1__6997_SHARP_));
}),ortho_diag)));
});
tic_tac_toe.board.winner_2d_QMARK_ = (function tic_tac_toe$board$winner_2d_QMARK_(board,character){
var or__5025__auto__ = tic_tac_toe.board.win_row_QMARK_.call(null,board,character);
if(cljs.core.truth_(or__5025__auto__)){
return or__5025__auto__;
} else {
var or__5025__auto____$1 = tic_tac_toe.board.win_column_QMARK_.call(null,board,character);
if(cljs.core.truth_(or__5025__auto____$1)){
return or__5025__auto____$1;
} else {
return tic_tac_toe.board.win_diag_QMARK_.call(null,board,character);
}
}
});
tic_tac_toe.board.win_3d_panel_QMARK_ = (function tic_tac_toe$board$win_3d_panel_QMARK_(board,character){
return cljs.core.some.call(null,(function (p1__7004_SHARP_){
return tic_tac_toe.board.winner_2d_QMARK_.call(null,p1__7004_SHARP_,character);
}),board);
});
tic_tac_toe.board.get_z_line = (function tic_tac_toe$board$get_z_line(board,p__7007){
var vec__7008 = p__7007;
var x = cljs.core.nth.call(null,vec__7008,(0),null);
var y = cljs.core.nth.call(null,vec__7008,(1),null);
var start = new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),x,y], null);
var step = new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(0),(0)], null);
var z_line_coordinates = cljs.core.take.call(null,cljs.core.count.call(null,board),cljs.core.iterate.call(null,cljs.core.partial.call(null,tic_tac_toe.board.next_location,step),start));
return cljs.core.map.call(null,(function (p1__7006_SHARP_){
return cljs.core.get_in.call(null,board,p1__7006_SHARP_);
}),z_line_coordinates);
});
tic_tac_toe.board.get_z_lines = (function tic_tac_toe$board$get_z_lines(board){
var xy_pairs = cljs.core.mapcat.call(null,(function (p1__7013_SHARP_){
return cljs.core.map.call(null,cljs.core.partial.call(null,cljs.core.vector,p1__7013_SHARP_),cljs.core.range.call(null,cljs.core.count.call(null,board)));
}),cljs.core.range.call(null,cljs.core.count.call(null,board)));
return cljs.core.map.call(null,(function (p1__7014_SHARP_){
return tic_tac_toe.board.get_z_line.call(null,board,p1__7014_SHARP_);
}),xy_pairs);
});
tic_tac_toe.board.win_3d_z_line_QMARK_ = (function tic_tac_toe$board$win_3d_z_line_QMARK_(board,character){
var z_lines = tic_tac_toe.board.get_z_lines.call(null,board);
return cljs.core.some.call(null,(function (p1__7019_SHARP_){
return tic_tac_toe.board.all_matching_QMARK_.call(null,p1__7019_SHARP_,character);
}),z_lines);
});
tic_tac_toe.board.z_line_lines = (function tic_tac_toe$board$z_line_lines(p__7021,size){
var vec__7022 = p__7021;
var x = cljs.core.nth.call(null,vec__7022,(0),null);
var y = cljs.core.nth.call(null,vec__7022,(1),null);
var start = new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),x,y], null);
var step = new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(0),(0)], null);
return cljs.core.vec.call(null,cljs.core.take.call(null,size,cljs.core.iterate.call(null,cljs.core.partial.call(null,tic_tac_toe.board.next_location,step),start)));
});
tic_tac_toe.board.z_plane_diags = (function tic_tac_toe$board$z_plane_diags(z,size){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [z,(0),(0)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(1),(1)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [z,(0),(size - (1))], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(1),(-1)], null)], null)], null);
});
tic_tac_toe.board.y_plane_diags = (function tic_tac_toe$board$y_plane_diags(y,size){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(0),y], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(1),(0)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(size - (1)),(0),y], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(-1),(1),(0)], null)], null)], null);
});
tic_tac_toe.board.x_plane_diags = (function tic_tac_toe$board$x_plane_diags(x,size){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),x,(0)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(0),(1)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),x,(size - (1))], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(0),(-1)], null)], null)], null);
});
tic_tac_toe.board.cube__GT_diag_start_steps = (function tic_tac_toe$board$cube__GT_diag_start_steps(size){
return new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(0),(0)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(1),(1)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(0),(size - (1))], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(1),(-1)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(size - (1)),(0)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(-1),(1)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(size - (1)),(size - (1))], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(-1),(-1)], null)], null)], null);
});
tic_tac_toe.board.get_zs = (function tic_tac_toe$board$get_zs(board){
var xy_pairs = cljs.core.mapcat.call(null,(function (p1__7041_SHARP_){
return cljs.core.map.call(null,cljs.core.partial.call(null,cljs.core.vector,p1__7041_SHARP_),cljs.core.range.call(null,cljs.core.count.call(null,board)));
}),cljs.core.range.call(null,cljs.core.count.call(null,board)));
return cljs.core.mapv.call(null,(function (p1__7042_SHARP_){
return tic_tac_toe.board.z_line_lines.call(null,p1__7042_SHARP_,cljs.core.count.call(null,board));
}),xy_pairs);
});
tic_tac_toe.board.__GT_line_coordinates = (function tic_tac_toe$board$__GT_line_coordinates(board,start,step){
return cljs.core.take.call(null,cljs.core.count.call(null,board),cljs.core.iterate.call(null,cljs.core.partial.call(null,tic_tac_toe.board.next_location,step),start));
});
tic_tac_toe.board.get_rows = (function tic_tac_toe$board$get_rows(board){
return cljs.core.mapv.call(null,(function (p1__7055_SHARP_){
return tic_tac_toe.board.__GT_line_coordinates.call(null,board,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [p1__7055_SHARP_,(0)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(1)], null));
}),cljs.core.range.call(null,cljs.core.count.call(null,board)));
});
tic_tac_toe.board.get_cols = (function tic_tac_toe$board$get_cols(board){
return cljs.core.mapv.call(null,(function (p1__7068_SHARP_){
return tic_tac_toe.board.__GT_line_coordinates.call(null,board,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),p1__7068_SHARP_], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(0)], null));
}),cljs.core.range.call(null,cljs.core.count.call(null,board)));
});
tic_tac_toe.board.get_diags = (function tic_tac_toe$board$get_diags(board){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [tic_tac_toe.board.__GT_line_coordinates.call(null,board,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(0)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(1)], null)),tic_tac_toe.board.__GT_line_coordinates.call(null,board,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(0),(cljs.core.count.call(null,board) - (1))], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(1),(-1)], null))], null);
});
tic_tac_toe.board.get_all_lines_2d = (function tic_tac_toe$board$get_all_lines_2d(board){
var rows = tic_tac_toe.board.get_rows.call(null,board);
var cols = tic_tac_toe.board.get_cols.call(null,board);
var diags = tic_tac_toe.board.get_diags.call(null,board);
return cljs.core.concat.call(null,rows,cols,diags);
});
tic_tac_toe.board.cube_diags = (function tic_tac_toe$board$cube_diags(size){
return tic_tac_toe.board.cube__GT_diag_start_steps.call(null,size);
});
tic_tac_toe.board.plane_x_diags = (function tic_tac_toe$board$plane_x_diags(size){
return cljs.core.mapcat.call(null,(function (p1__7085_SHARP_){
return tic_tac_toe.board.x_plane_diags.call(null,p1__7085_SHARP_,size);
}),cljs.core.range.call(null,size));
});
tic_tac_toe.board.plane_y_diags = (function tic_tac_toe$board$plane_y_diags(size){
return cljs.core.mapcat.call(null,(function (p1__7089_SHARP_){
return tic_tac_toe.board.y_plane_diags.call(null,p1__7089_SHARP_,size);
}),cljs.core.range.call(null,size));
});
tic_tac_toe.board.plane_z_diags = (function tic_tac_toe$board$plane_z_diags(size){
return cljs.core.mapcat.call(null,(function (p1__7095_SHARP_){
return tic_tac_toe.board.z_plane_diags.call(null,p1__7095_SHARP_,size);
}),cljs.core.range.call(null,size));
});
tic_tac_toe.board.get_all_3d_diags = (function tic_tac_toe$board$get_all_3d_diags(size){
return cljs.core.concat.call(null,tic_tac_toe.board.cube_diags.call(null,size),tic_tac_toe.board.plane_x_diags.call(null,size),tic_tac_toe.board.plane_y_diags.call(null,size),tic_tac_toe.board.plane_z_diags.call(null,size));
});
tic_tac_toe.board.line_maker = (function tic_tac_toe$board$line_maker(board,p__7098){
var vec__7099 = p__7098;
var start = cljs.core.nth.call(null,vec__7099,(0),null);
var step = cljs.core.nth.call(null,vec__7099,(1),null);
return tic_tac_toe.board.__GT_line_coordinates.call(null,board,start,step);
});
tic_tac_toe.board.instructions__GT_line_coords = (function tic_tac_toe$board$instructions__GT_line_coords(board,instructions){
return cljs.core.mapv.call(null,(function (p1__7102_SHARP_){
return tic_tac_toe.board.line_maker.call(null,board,p1__7102_SHARP_);
}),instructions);
});
tic_tac_toe.board.add_z_to_planes = (function tic_tac_toe$board$add_z_to_planes(z,coords_panel){
return cljs.core.map.call(null,(function (group){
return cljs.core.mapv.call(null,(function (coord){
return cljs.core.vec.call(null,cljs.core.cons.call(null,z,coord));
}),group);
}),coords_panel);
});
tic_tac_toe.board.get_all_lines_3d = (function tic_tac_toe$board$get_all_lines_3d(board){
var size = cljs.core.count.call(null,board);
var diags = tic_tac_toe.board.get_all_3d_diags.call(null,size);
var diag_line_coords = tic_tac_toe.board.instructions__GT_line_coords.call(null,board,diags);
var z_lines = tic_tac_toe.board.get_zs.call(null,board);
var panel_lines_2ds = cljs.core.mapv.call(null,(function (p1__7109_SHARP_){
return tic_tac_toe.board.get_all_lines_2d.call(null,p1__7109_SHARP_);
}),board);
var panel_lines = cljs.core.apply.call(null,cljs.core.concat,cljs.core.map_indexed.call(null,tic_tac_toe.board.add_z_to_planes,panel_lines_2ds));
return cljs.core.concat.call(null,diag_line_coords,z_lines,panel_lines);
});
tic_tac_toe.board.get_all_lines = (function tic_tac_toe$board$get_all_lines(board){
if(tic_tac_toe.board.board_3d_QMARK_.call(null,board)){
return tic_tac_toe.board.get_all_lines_3d.call(null,board);
} else {
return tic_tac_toe.board.get_all_lines_2d.call(null,board);
}
});
tic_tac_toe.board.start_step__GT_values = (function tic_tac_toe$board$start_step__GT_values(board,start,step){
return cljs.core.map.call(null,(function (p1__7113_SHARP_){
return cljs.core.get_in.call(null,board,p1__7113_SHARP_);
}),tic_tac_toe.board.__GT_line_coordinates.call(null,board,start,step));
});
tic_tac_toe.board.win_3d_diag_QMARK_ = (function tic_tac_toe$board$win_3d_diag_QMARK_(board,character){
var size = cljs.core.count.call(null,board);
var all_diags = tic_tac_toe.board.get_all_3d_diags.call(null,size);
var diag_values = cljs.core.map.call(null,(function (p1__7118_SHARP_){
return tic_tac_toe.board.start_step__GT_values.call(null,board,cljs.core.first.call(null,p1__7118_SHARP_),cljs.core.second.call(null,p1__7118_SHARP_));
}),all_diags);
return cljs.core.boolean$.call(null,cljs.core.some.call(null,(function (p1__7119_SHARP_){
return tic_tac_toe.board.all_matching_QMARK_.call(null,p1__7119_SHARP_,character);
}),diag_values));
});
tic_tac_toe.board.winner_3d_QMARK_ = (function tic_tac_toe$board$winner_3d_QMARK_(board,character){
var or__5025__auto__ = tic_tac_toe.board.win_3d_panel_QMARK_.call(null,board,character);
if(cljs.core.truth_(or__5025__auto__)){
return or__5025__auto__;
} else {
var or__5025__auto____$1 = tic_tac_toe.board.win_3d_z_line_QMARK_.call(null,board,character);
if(cljs.core.truth_(or__5025__auto____$1)){
return or__5025__auto____$1;
} else {
return tic_tac_toe.board.win_3d_diag_QMARK_.call(null,board,character);
}
}
});
tic_tac_toe.board.winner_QMARK_ = (function tic_tac_toe$board$winner_QMARK_(board,character){
if(cljs.core._EQ_.call(null,(2),tic_tac_toe.board.get_board_complexity.call(null,board))){
return tic_tac_toe.board.winner_2d_QMARK_.call(null,board,character);
} else {
return tic_tac_toe.board.winner_3d_QMARK_.call(null,board,character);
}
});
tic_tac_toe.board.evaluate_board = (function tic_tac_toe$board$evaluate_board(p__7123){
var map__7124 = p__7123;
var map__7124__$1 = cljs.core.__destructure_map.call(null,map__7124);
var state = map__7124__$1;
var board = cljs.core.get.call(null,map__7124__$1,new cljs.core.Keyword(null,"board","board",-1907017633));
var active_player_index = cljs.core.get.call(null,map__7124__$1,new cljs.core.Keyword(null,"active-player-index","active-player-index",-93298655));
var players = cljs.core.get.call(null,map__7124__$1,new cljs.core.Keyword(null,"players","players",-1361554569));
if(cljs.core.truth_(tic_tac_toe.board.winner_QMARK_.call(null,board,cljs.core.get_in.call(null,players,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [active_player_index,new cljs.core.Keyword(null,"character","character",380652989)], null))))){
return cljs.core.assoc.call(null,state,new cljs.core.Keyword(null,"status","status",-1997798413),new cljs.core.Keyword(null,"winner","winner",714604679));
} else {
if(cljs.core.not.call(null,tic_tac_toe.board.any_space_available_QMARK_.call(null,board))){
return cljs.core.assoc.call(null,state,new cljs.core.Keyword(null,"status","status",-1997798413),new cljs.core.Keyword(null,"tie","tie",-487201694));
} else {
return state;

}
}
});

//# sourceMappingURL=board.js.map
