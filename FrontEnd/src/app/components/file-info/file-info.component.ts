import {Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {FileService} from '../../core/services/file.service';
import {ActivatedRoute} from '@angular/router';
import {FullFileInfo} from '../../core/model/full-file-info';
import {formatDate} from '@angular/common';
import {WebSocketService} from '../../core/services/web-socket.service';

@Component({
  selector: 'app-file-info',
  templateUrl: './file-info.component.html',
  styleUrls: ['./file-info.component.css']
})
export class FileInfoComponent implements OnInit {

  id: string;
  fullFileInfo: FullFileInfo = new FullFileInfo();
  alreadyEditByMe: boolean = false;
  sliderValue=false;

  constructor(private fileService: FileService, private activatedRoute: ActivatedRoute,
              private webSocketService: WebSocketService) {
  }

  @HostListener('window:beforeunload', [ '$event' ])
  beforeUnloadHander(event) {
    this.webSocketService.unblockFileMethod(this.id);
  }

  ngOnInit() {
    this.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.getFile();
    this.webSocketService.blockFile.subscribe(() => {
      this.getFile();
    });
    this.webSocketService.unblockFile.subscribe(() => this.getFile());
  }

  private getFile() {
    this.fileService.getSingleFileInfo(this.id).subscribe((res: FullFileInfo) => {
      this.fullFileInfo = res;
      this.fullFileInfo.modifiedDate = formatDate(this.fullFileInfo.modifiedDate, 'dd-MM-yyyy hh:mm:ss a', 'en-US');
      this.fullFileInfo.createdDate = formatDate(this.fullFileInfo.createdDate, 'dd-MM-yyyy hh:mm:ss a', 'en-US');
    });
  }

  saveFile() {
    this.fileService.updateFile(this.fullFileInfo).subscribe((res: FullFileInfo) => {
      this.fullFileInfo = res;
      this.fullFileInfo.modifiedDate = formatDate(this.fullFileInfo.modifiedDate, 'dd-MM-yyyy hh:mm:ss a', 'en-US');
      this.fullFileInfo.createdDate = formatDate(this.fullFileInfo.createdDate, 'dd-MM-yyyy hh:mm:ss a', 'en-US');
      this.webSocketService.unblockFileMethod(this.id);
      this.alreadyEditByMe=false;
      this.sliderValue=!this.sliderValue;
    });
  }

  editMode() {
    this.sliderValue = !this.sliderValue;
    this.alreadyEditByMe = !this.alreadyEditByMe;
    if (this.alreadyEditByMe)
      this.webSocketService.blockFileMethod(this.id);
    else
      this.webSocketService.unblockFileMethod(this.id);
  }

  checkDisabled() {
    if (this.fullFileInfo.isEditing && this.alreadyEditByMe)
      return false;
    else if (!this.fullFileInfo.isEditing && !this.alreadyEditByMe)
      return true;
    else if (this.fullFileInfo.isEditing)
      return true;
  }

}
