import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FileService} from '../../core/services/file.service';
import {FileInfo} from '../../core/model/file-info';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {MatDialog, MatPaginator, MatTableDataSource} from '@angular/material';
import {DialogComponent} from '../dialog/dialog.component';
import {formatDate} from '@angular/common';
import {WebSocketService} from '../../core/services/web-socket.service';

@Component({
  selector: 'app-file-grid',
  templateUrl: './file-grid.component.html',
  styleUrls: ['./file-grid.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class FileGridComponent implements OnInit {

  dataSource;
  columnsToDisplay = ['name', 'version', 'modifiedBy', 'modifiedDate', 'size','isEditing'];
  expandedElement = new FileInfo();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private fileService: FileService,  private webSocketService: WebSocketService) {
  }

  ngOnInit() {
    this.getFiles();
    this.webSocketService.newFile.subscribe(() => this.getFiles());
    this.webSocketService.blockFile.subscribe(() => this.getFiles());
    this.webSocketService.unblockFile.subscribe(() => this.getFiles());
  }

  private getFiles() {
    this.fileService.getFileInfo().subscribe((res: FileInfo[]) => {
      res.forEach(value => {
        value.modifiedDate = formatDate(value.modifiedDate, 'dd-MM-yyyy hh:mm:ss a', 'en-US');
        value.createdDate = formatDate(value.createdDate, 'dd-MM-yyyy hh:mm:ss a', 'en-US');
      });
      this.dataSource = new MatTableDataSource<FileInfo>(res);
      this.dataSource.paginator = this.paginator;
      console.log(res);
    });
  }

  expand(element) {
    if (this.expandedElement === element)
      this.expandedElement = null;
    else
      this.expandedElement = element;
  }

}
