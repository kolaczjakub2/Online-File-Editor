import  {Component, OnInit} from '@angular/core';
import {CreateFileInfo} from '../../core/model/create-file-info';
import {FileService} from '../../core/services/file.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {WebSocketService} from '../../core/services/web-socket.service';


@Component({
  selector: 'app-add-file-form',
  templateUrl: './add-file-form.component.html',
  styleUrls: ['./add-file-form.component.css']
})
export class AddFileFormComponent implements OnInit {

  fileInfo: CreateFileInfo = new CreateFileInfo();

  constructor(private fileService: FileService, private router: Router,
              public snackBar: MatSnackBar, private webSocketService:WebSocketService) {
  }

  ngOnInit() {
  }

  saveFile() {
    console.log(this.fileInfo);
    this.fileService.saveFile(this.fileInfo).subscribe(
      (res: string) => {
        this.webSocketService.createFile();
        this.router.navigateByUrl('file/' + res);
      }, error1 => {
        let snackBarRef = this.snackBar.open('Cannot save file', null, {duration: 2000,});
      }
    );
  }
}
