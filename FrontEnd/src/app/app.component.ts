import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {DialogComponent} from './components/dialog/dialog.component';
import {FileService} from './core/services/file.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'FrontEnd';

  constructor(public dialog: MatDialog,
              public fileService: FileService) {
  }

  ngOnInit(): void {
    const dialogRef = this.dialog.open(DialogComponent);

    dialogRef.afterClosed().subscribe((nickname: string) => {
      localStorage.setItem('nickname', nickname);
      this.fileService.addToChat(nickname).subscribe();
    });
  }
}
