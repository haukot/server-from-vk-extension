-module(echo_server).
-author("Jesse E.I. Farmer <jesse@20bits.com>").

-export([start/0, loop/1, loop1/3]).

% Специфичный код эхо-сервера
start() ->
    socket_server:start(?MODULE, 7000, {?MODULE, loop}).

loop(Socket) ->
    case gen_tcp:recv(Socket, 0) of
        {ok, Data} ->
        	<<X>> = Data,
        	io:format("From client: ~p~n" , [X]),
			{ok,Host}=inet:gethostname().
			JavaServer = {dummy, list_to_atom("javaserver@"++Host)}.
			Res = gen_server:call(JavaServer, {addNode, X}).
            gen_tcp:send(Socket, <<Res>>),
            loop(Socket);
        {error, closed} ->
            ok
    end.

loop1(Socket, X, Y) ->
	case gen_tcp:send(Socket, <<X, Y>>) of
    ok ->
    	case gen_tcp:recv(Socket, 0) of
        {ok, Data} ->
        	Data;
        {error, closed} ->
            oook
    	end;
    {error, closed} ->
        ok;
    true -> loop1(Socket, X, Y)
	end.