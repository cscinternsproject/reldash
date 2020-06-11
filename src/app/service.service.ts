import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {IParameter} from './BoardData';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private url: string ='http://localhost:8080/ReleaseDashboard';
   private _url : string ='http://localhost:8080/ReleaseDashboard';
  constructor(private http: HttpClient) { }

  getParameter(): Observable<IParameter>
  {
    return this.http.post<IParameter>(this.url,{"project":"JIRARD",
	"version":"Release1"
},{headers:new HttpHeaders({'content-type': 'application/json'})});
  }

   getProgress(): Observable<IParameter>
  {
  return this.http.post<IParameter>(this.url,{"project":"JIRARD",
	"version":"Release1"
},{headers:new HttpHeaders({'content-type': 'application/json'})});
  }

}
