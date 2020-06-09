import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {IParameter} from './BoardData';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private url: string ='https://my-json-server.typicode.com/ZaidBJ/Sample-Json/ReleaseDate'
  constructor(private http: HttpClient) { }

  getParameter(): Observable<IParameter>
  {
    return this.http.get<IParameter>(this.url);
  }
}
