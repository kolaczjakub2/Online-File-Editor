import {Injectable} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Subject} from 'rxjs';
import {BlockFile} from '../model/block-file-dto';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private serverUrl = 'http://192.168.1.9:8080/socket';
  private StompClient;
  private newFileStompClient;
  private blockFileStompClient;
  private unblockFileStompClient;
  newMessage = new Subject();
  newFile = new Subject();
  blockFile = new Subject();
  unblockFile = new Subject();

  constructor() {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    let ws1 = new SockJS(this.serverUrl);
    let ws2 = new SockJS(this.serverUrl);
    let ws3 = new SockJS(this.serverUrl);
    this.StompClient = Stomp.over(ws);
    this.newFileStompClient = Stomp.over(ws1);
    this.blockFileStompClient = Stomp.over(ws2);
    this.unblockFileStompClient = Stomp.over(ws3);
    let that = this;
    this.StompClient.connect({}, function (frame) {
      that.StompClient.subscribe('/chat', (message) => {
        if (message.body) {
          that.newMessage.next(message.body);
        }
      });
    });
    this.newFileStompClient.connect({}, function (frame) {
      that.newFileStompClient.subscribe('/newFile', (message) => {
        if (message.body) {
          that.newFile.next(message.body);
        }
      });
    });
    this.blockFileStompClient.connect({}, function (frame) {
      that.blockFileStompClient.subscribe('/blockFile', (message) => {
        if (message.body) {
          that.blockFile.next(message.body);
        }
      });
    });
    this.unblockFileStompClient.connect({}, function (frame) {
      that.unblockFileStompClient.subscribe('/unblockFile', (message) => {
        if (message.body) {
          that.unblockFile.next(message.body);
        }
      });
    });
  }


  sendMessage(message, to: string) {
    const from = localStorage.getItem('nickname');
    let body = JSON.stringify({message, from, to});
    this.StompClient.send('/app/send/message', {}, body);
  }

  createFile() {
    this.newFileStompClient.send('/app/file/create', {}, 'nowy pliczek');
  }

  blockFileMethod(id: string) {
    const name = localStorage.getItem('nickname');
    let body = JSON.stringify({id, name});
    this.blockFileStompClient.send('/app/file/block', {}, body);
  }

  unblockFileMethod(id: string) {
    this.unblockFileStompClient.send('/app/file/unblock', {}, id);
  }
}
