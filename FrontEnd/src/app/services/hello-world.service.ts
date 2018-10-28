import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HelloWorldService {

  private url = 'http://localhost:8080/helloWorld';

  constructor(private http: HttpClient) {
  }

  getHelloWorld(name: string) {
    return this.http.get(this.url + '/' + name, {responseType: 'text'});
  }
}
