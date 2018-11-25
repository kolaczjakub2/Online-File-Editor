import {Component, OnInit, ViewChild} from '@angular/core';
import {WebSocketService} from '../../core/services/web-socket.service';
import {HostListener} from '@angular/core';
import {FileService} from '../../core/services/file.service';
import {Message} from '../../core/model/message';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  screenHeight: number;
  messages: string = '';
  @ViewChild('input') input;
  @ViewChild('select') select;
  users: string[];
  to: string;

  constructor(private webSocketService: WebSocketService,
              private fileService: FileService) {
    this.onResize();
  }

  ngOnInit() {
    this.autogrow();
    this.getChatUsers();
    this.webSocketService.newMessage.subscribe((value: string) => {
      let value1 = JSON.parse(value);
      if (value1.to === localStorage.getItem('nickname') || value1.from === localStorage.getItem('nickname')) {
        this.messages += `${value1.from} [${value1.time}]: ${value1.message}\n`;
      }
      this.autogrow();
    });
  }

  autogrow() {
    let textArea = document.getElementById('textarea');
    textArea.style.height = this.screenHeight - 300 + 'px';
    textArea.style.overflowY = 'auto';
    textArea.scrollTop = textArea.scrollHeight;

  }

  sendMessage(value) {
    this.input.nativeElement.value = '';
    this.webSocketService.sendMessage(value, this.to);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event?) {
    this.screenHeight = window.innerHeight;
  }

  getChatUsers() {
    this.fileService.getChatUsers().subscribe((value: string[]) => {
      this.users = value.filter(value1 => value1 !== localStorage.getItem('nickname'));
    });
  }
}
