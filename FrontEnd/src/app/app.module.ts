import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FileGridComponent} from './components/file-grid/file-grid.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FileService} from './core/services/file.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {MatNativeDateModule} from '@angular/material';
import {MaterialModule} from './angular-material.module';
import {DialogComponent} from './components/dialog/dialog.component';
import {AddFileFormComponent} from './components/add-file-form/add-file-form.component';
import {FileInfoComponent} from './components/file-info/file-info.component';
import {CommonModule} from '@angular/common';

const appRoutes: Routes = [
  {
    path: 'file',
    children: [
      {path: 'all', component: FileGridComponent},
      {path: 'add', component: AddFileFormComponent},
      {path: ':id', component: FileInfoComponent}
    ]
  },
  {path:'**',redirectTo:'/file/all'}

];

@NgModule({
  declarations: [
    AppComponent,
    FileGridComponent,
    DialogComponent,
    AddFileFormComponent,
    FileInfoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    MaterialModule,
    MatNativeDateModule,
    CommonModule
  ],
  entryComponents: [DialogComponent],
  providers: [FileService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
