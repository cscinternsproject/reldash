import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {IParameter} from './BoardData';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private url: string ='https://my-json-server.typicode.com/ZaidBJ/Front/projects';
   private _url : string ='https://my-json-server.typicode.com/ZaidBJ/Front/projects';
   private _url1 : string = 'https://my-json-server.typicode.com/ZaidBJ/Front/';
  private _url2 : string = 'https://my-json-server.typicode.com/ZaidBJ/Sample-Json/Release';
  private _url3 : string = 'https://my-json-server.typicode.com/ZaidBJ/Sample-Json/ReleaseDate'
  constructor(private http: HttpClient) { }
 
  getParameter()
  {
    return this.http.get(this.url)
  }
  getName(key)
  {
    
    return this.http.get(this._url1 + key);
  }
   
  getComp(para)
  {
    
    return this.http.get(this._url1 + para);
  }


   getProgress()
  {
  return this.http.get(this._url2);
  }
  getDate()
  {
    return this.http.get(this._url3);
  }
}