#!/usr/bin/octave 

%% This is a software to can connect to the java application 

%% Main function
function start()
  disp('Load library')
  pkg load sockets
  disp('Loading 5 seconds....')
  pause(5); % Important for Server needs to be started first!
  
  %% Establish connection with Java
  disp("Press CTRL+C to break");
  [s] = openClientConnection();
  
  while(true)
    
    %% Wait for data
    [data, s] = recieving(s);
      
    %% Send back
    send(s, data); 
    
  endwhile
  
endfunction

%% This function will reciev from java
function [data, s] = recieving(s)
  startTime = 0.0; % Clock
  count = -1; % No data
  endTime = 5.0; % 5 seconds as end time for reconnecting 
  while(count <= 0)
    [data, count] = recv(s, 1000, MSG_DONTWAIT); % 1000 = length of maximum data limit
    % If we got no data after more then 3 seconds, re-connect
    pause(0.01);
    startTime = startTime + 0.01;
    
    if(startTime > endTime)
      disp('reconnecting')
      [s] = openClientConnection();
      data = uint8(48); % Sending back 0 in ASCII
      break; % Break the while loop and go to send
    endif
  endwhile

endfunction

%% This function will open a client connection with the server
function [s] = openClientConnection()
  s = socket(AF_INET, SOCK_STREAM, 0);
  addr = struct("addr", "127.0.0.1", "port", 5000);
  
  % This statement will keep connecting until it got its connection
  notConnected = true;
  while (notConnected)
    try
      connect(s, addr);
      notConnected = false; % Break the while loop
    catch err
      notConnected = true;
      warning(err.identifier, err.message); % Print message
    end_try_catch
  endwhile
  
endfunction

%% This function will close the connection
function closeConnection(s)
  disp("disconnecting");
  disconnect(s);
endfunction