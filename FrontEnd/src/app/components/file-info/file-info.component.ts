import {Component, OnInit} from '@angular/core';
import {FileService} from '../../core/services/file.service';
import {ActivatedRoute} from '@angular/router';
import {FullFileInfo} from '../../core/model/full-file-info';
import {formatDate} from '@angular/common';

@Component({
  selector: 'app-file-info',
  templateUrl: './file-info.component.html',
  styleUrls: ['./file-info.component.css']
})
export class FileInfoComponent implements OnInit {

  id: string;
  fullFileInfo: FullFileInfo;

  constructor(private fileService: FileService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.fileService.getSingleFileInfo(this.id).subscribe((res: FullFileInfo) => {

      this.fullFileInfo = res;
      this.fullFileInfo.modifiedDate = formatDate( this.fullFileInfo.modifiedDate, 'dd-MM-yyyy hh:mm:ss a','en-US')
      this.fullFileInfo.createdDate = formatDate( this.fullFileInfo.createdDate, 'dd-MM-yyyy hh:mm:ss a','en-US')
    });
    }

    saveFile(){
    this.fileService.updateFile(this.fullFileInfo).subscribe((res: FullFileInfo) => {

      this.fullFileInfo = res;
      this.fullFileInfo.modifiedDate = formatDate( this.fullFileInfo.modifiedDate, 'dd-MM-yyyy hh:mm:ss a','en-US')
      this.fullFileInfo.createdDate = formatDate( this.fullFileInfo.createdDate, 'dd-MM-yyyy hh:mm:ss a','en-US')
    })
    }

}
