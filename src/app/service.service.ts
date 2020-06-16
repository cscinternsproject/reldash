import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {IParameter} from './BoardData';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private url: string ='http://localhost:8080/getProjList';
   private _url : string ='http://localhost:8080/getTeams';
   private _url1 : string = 'http://localhost:8080/getReleases/';
  private _url2 : string = 'https://my-json-server.typicode.com/ZaidBJ/Sample-Json/Release';
  private _url3 : string = 'http://localhost:8080/ReleaseDashboard';

  private messageSource = new BehaviorSubject({color:'green'});
  currentMessage = this.messageSource.asObservable();

  constructor(private http: HttpClient) { }

  changeMessage(message) {
    this.messageSource.next(message)
  }



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

    return this.http.get(this._url);
  }


   getProgress()
  {
  return this.http.get(this._url2);
  }

  getRules(info){
return info;
  }
  getDate(project,release)
  {  console.log(project+" "+release)
    return this.http.post(this._url3,{"project":project,"version":release},
    {headers:new HttpHeaders({'content-type': 'application/json'})});
  }
}
