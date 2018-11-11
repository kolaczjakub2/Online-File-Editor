import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreateFileInfo} from '../model/create-file-info';
import {FullFileInfo} from '../model/full-file-info';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private url = 'http://192.168.1.9:8080/files';

  constructor(private http: HttpClient) {
  }

  getFileInfo(): Observable<any> {
    return this.http.get(this.url);
  }

  getSingleFileInfo(id: string) {
    return this.http.get(this.url + '/' + id);
  }

  saveFile(body: CreateFileInfo) {
    return this.http.post(this.url, body);
  }

  updateFile(fullFileInfo: FullFileInfo) {
    return this.http.put(this.url + '/' + fullFileInfo.id, fullFileInfo);
  }

  addToChat(name: string) {
    return this.http.post(this.url + `/chat/${name}`, null);
  }

  getChatUsers() {
    return this.http.get(this.url + `/chat`,);
  }
}
