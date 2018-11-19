var date = null;
var msg = [];
var ownMsgId = [];
var ITEM_ENUM = {
		felles : "FELLES",
		SOC1 : "SOC1",
		SOC2 : "SOC2",
		SOC3 : "SOC3",
		SOC4 : "SOC4",
		SOC5 : "SOC5",
		all : "all"
}
var audioMsgFor = ITEM_ENUM.felles;
var stompClient;
var CLIENT = {
	webSocketController : {
		connectAndSubscribe : function () {
			console.log("Trying to connect");
		    var socket = new SockJS('/msg-websocket');
		    stompClient = Stomp.over(socket);
		    stompClient.connect("",this.onConnectSuccess ,function(message){
		    		if(message.includes("Lost connection")){
		    			swal("Connection lost", "Try a refresh", "warning");
		    		}
		    });
		},
		onConnectSuccess : function (frame) {
			console.log("setting up subscription to /topic/messages");
		    stompClient.subscribe('/topic/messages', function(stomp){
				console.log("Messages recived: "+ stomp);
				CLIENT.ioController.prependMessage(JSON.parse(stomp.body));
		    } );
		},
		onMessageReceived :  function (stomp) {
			
		},
		disconnect : function() {
		    if (stompClient !== null) {
		        stompClient.disconnect();
		    }
		    console.log("Disconnected");
		},
		sendMessage : function (message) {
			   stompClient.send("/app/save", {}, JSON.stringify(message));
			   CLIENT.ioController.clearAndHideMsg();
		}
	},
	ioController : {
		 initSaveButton : function(){
			$('#save').click(function() {
				var message = {};
				message.sentTo = $('#to').val();
				message.sentBy = $('#from').val();
				message.message = $('#message').val();
				message.requiresAction = $('#requiresAction').is(':checked');
				CLIENT.webSocketController.sendMessage(message);
			})
			
			$('#fab-add').click(function() {
				$("#newModal").modal();
			})
		},
		clearAndHideMsg : function(){
			$('#message').val("");
			$("#newModal").modal('hide');
			$('#requiresAction').prop('checked', false);
		},
		playAudio : function (item_id) {			
			if ($.inArray(item_id, ownMsgId) === -1) {
				var audio = document.getElementById("audio");
				audio.play();
			}
		},
		showNotification : function(messageObject){
			
			//var img = '/to-do-notifications/img/icon-128.png';
			var text = "From: "+ messageObject.sentBy + "\nTo: "+ messageObject.sentTo + "\nMessage: "+ messageObject.message + "\n";

			var notification = new Notification('New message', { body: text, sound: '/assets/mp3/notification.mp3', silent:false});

				
		},

		notifyUser : function (item){
			if(item.sentTo === audioMsgFor || audioMsgFor === ITEM_ENUM.all){
				this.playAudio(item.id);

				/* if (Notification.permission === "granted" &&  !navigator.userAgent.includes("ndroid")) {
					 
					 //if user is allowed to display push notifications,show a notification
					 this.showNotification(item);	 
				}*/
			}
		},
		prependMessage : function (item) {
				//msg.push(item.id);
				
				console.log("prepending message" +JSON.stringify(item));
				this.notifyUser(item);
				
				date = moment(item.savedAt).format(
						'DD/MM/YYYY/HH:mm:ss');
				var $tr = $('<tr>', {
					class : 'blink'
				});
	
				var td = '<td>'
						+ moment(item.savedAt)
								.format('DDHHmmMMMYYYY')
						+ '</td><td>' + item.sentBy + '</td><td>'
						+ item.sentTo + '</td><td>' + item.message
						+ '</td>';
	
				$tr.append(td);

				if (item.sentTo === ITEM_ENUM.SOC1) {
					$("#soc1_msg_body").prepend($tr);
				}
				if (item.sentTo === ITEM_ENUM.SOC2) {
					$("#soc2_msg_body").prepend($tr);
				}
				if (item.sentTo === ITEM_ENUM.SOC3) {
					$("#soc3_msg_body").prepend($tr);
				}
				if (item.sentTo === ITEM_ENUM.SOC4) {
					$("#soc4_msg_body").prepend($tr);
				}
				if (item.sentTo === ITEM_ENUM.SOC5) {
					$("#soc5_msg_body").prepend($tr);
				}
				if (item.sentTo === "DropIn") {
					$("#soc6_msg_body").prepend($tr);
				}
				if (item.sentTo === ITEM_ENUM.felles) {
					$("tbody").prepend($tr);
				} else {
					$("#soc0_msg_body").prepend(
							'<tr class="blink"><td>'
									+ moment(item.savedAt).format(
											'DDnovHHmmss')
									+ '</td><td>' + item.sentBy
									+ '</td><td>' + item.sentTo
									+ '</td><td>' + item.message
									+ '</td></tr>');
				}
		},
		updateAllMessages : function(data, textStatus, xhr) {
	
			if (JSON.stringify(data).includes("DOCTYPE")) {
				location.reload();
			}
			$(data).each(function(pos, item) {
						if ($.inArray(item.id, msg) === -1) {
							CLIENT.ioController.prependMessage(item);
						}
			});
		},
		requestNotificationPermissions: function(){
//			navigator.serviceWorker.register('assets/js/sw.js');
			Notification.requestPermission(function(){
				 // Let's check if the browser supports notifications
				 
				  if (!("Notification" in window)) {
				    alert("This browser does not support system notifications");
				  }

				  // Let's check whether notification permissions have already been granted
				  else if (Notification.permission === "granted") {

				    // If it's okay let's create a notification
				  //  var notification = new Notification("Hi there!");
				  }

				  // Otherwise, we need to ask the user for permission
				  else if (Notification.permission !== 'denied') {
					  
				    Notification.requestPermission(function (permission) {
				      // If the user accepts, let's create a notification
				      if (permission === "granted") {
				       // var notification = new Notification("Hi there!");
				      }
				    });
				  }

				  // Finally, if the user has denied notifications and you 
				  // want to be respectful there is no need to bother them any more.
			});
		}
		
	}
}


function getUrlMessages(date, cell) {
	var url = "/api/message?"
	if (date) {
		//var dateString = moment(date).format('DD/MM/YYYY/HH:mm:ss');
		url += "after=" + date;
	}
	if (cell) {
		url += "to=" + cell;
	}

	return url;
}

function fetchMessages(url, callback) {

	$.ajax({
		url : url,
		type : "GET",
		contentType : "application/json; charset=utf-8",
		success : callback,
		error : function(textStatus, errorThrown) {
			swal("Connection lost", "Try a refresh", "warning");
		}
	});
}




$("#audio-chooser").change(function () {
	    var str = "";
	    $( "#audio-chooser option:selected" ).each(function() {
			audioMsgFor = $(this).attr('data-target');
	    });
		
 });

function getAllSavedMessages(){
	fetchMessages(getUrlMessages(null, null), CLIENT.ioController.updateAllMessages);
}
/*
function startFetchingMessages(){
	fetchMessages(getUrlMessages(date, null), updateAllMessages);
	window.setInterval(function() {
		fetchMessages(getUrlMessages(date, null), updateAllMessages);
	}, 3000);
}
*/
$('a').click(function(e) {
	e.preventDefault();
	$('a').removeClass('active');
	$('.msg-container').hide();

	$($(this).attr('data-target')).show();
	$(this).addClass('active');
});
