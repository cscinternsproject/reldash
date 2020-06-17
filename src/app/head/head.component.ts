import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';
import {BehaviorSubject} from 'rxjs';
declare const bar: any;

@Component({
  selector: 'app-head',
  templateUrl: './head.component.html',
  styleUrls: ['./head.component.css',
  '../../../node_modules/bootstrap/dist/css/bootstrap.min.css']
})
export class HeadComponent implements OnInit {
   Data : any;
   Projectselect : any;
   Releaseselect : any;
   Compselect : any;
   Comp : any;
   Arg : any;
   Response : any;
   Info : any;
   Bar : any;
   Call : any;
   progressData:any
  constructor(private dataservice: ServiceService) { }



  ngOnInit(): void {

    this.dataservice.currentMessage.subscribe(message => this.progressData = message);

    this.Info={rstartDate:"",rendDate:"",sstartDate:"",sendDate:""};
    this.Call={ "Capacty": "",
    "sprintColor": "",
    "releasePerc":""};
    this.dataservice.getProjects()
    .subscribe(data => {

    this.Data = data;

     });
  }


  date(Info){

    Info.rendDate = (new Date(Info.rendDate)).toDateString();
    Info.rstartDate = new Date(Info.rstartDate).toDateString();
    Info.sstartDate =(new Date(Info.sstartDate)).toDateString();
    Info.sendDate = (new Date(Info.sendDate)).toDateString();
    this.Info.releasePerc=(1-parseFloat( this.Info.releasePerc.toFixed(2)))*100;
    bar(parseInt((this.Info.releasePerc)));
    this.dataservice.sendMessage(this.Info)



  }
  onProjectselect() : any{
       this.dataservice.getReleases(this.Projectselect)
    .subscribe(response => {this.Response = response;
      console.log(response);

  })

}
onReleaseselect() : any{
  console.log(this.Releaseselect);
  this.dataservice.getComp(this.Releaseselect)
  .subscribe(response => {this.Comp = response;
    console.log(response);

})

}
onCompselect() : any{
  console.log(this.Compselect);
  this.dataservice.getComp(this.Compselect)
  .subscribe(response => {this.Arg = response;
    console.log(response);

})
}

onButtonselect() : any
{
  this.dataservice.getReleaseData(this.Projectselect,this.Releaseselect)
  .subscribe(data => {this.Info = data;
  console.log(data);
  this.date(this.Info);



  })








}




}







