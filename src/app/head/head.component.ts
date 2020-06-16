import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';
declare const bar: any;

@Component({
  selector: 'app-head',
  templateUrl: './head.component.html',
  styleUrls: ['./head.component.css',
  '../../../node_modules/bootstrap/dist/css/bootstrap.min.css']
})
export class HeadComponent implements OnInit {
   Data : any;
   Projectselect : string;
   Releaseselect : any;
   Compselect : any;
   Comp : any;
   Arg : any;
   Response : any;
   Info : any;
   Bar : any;
   Call : any;

  constructor(private dataservice: ServiceService) { }



  ngOnInit(): void {


    this.Info={rstartDate:"",rendDate:"",sstartDate:"",sendDate:""};
    this.Call={ "Capacty": "",
    "sprintColor": "",
    "releasePerc":""};
    this.dataservice.getParameter()
    .subscribe(data => {


    this.Data = data;
    console.log(this.Data);

    console.log(this.Info);
     });
  }


  date(Info){

    Info.rendDate = (new Date(Info.rendDate)).toDateString();
    Info.rstartDate = new Date(Info.rstartDate).toDateString();
    Info.sstartDate =(new Date(Info.SstartDate)).toDateString();
    Info.sendDate = (new Date(Info.SendDate)).toDateString();
   // Data.project ="Release Dashboard";
    //Data.release ="Release1";
    console.log("datr")


  }
  onProjectselect() : any{
    console.log(this.Projectselect);
    this.dataservice.getName(this.Projectselect)
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
  this.dataservice.getDate()
  .subscribe(data => {this.Info = data;
  console.log(data);

  this.date(this.Info);

  })
  this.dataservice.getProgress()
     .subscribe(data => {this.Call = data;
     console.log(this.Call);

     this.Call.releasePerc=(1-parseFloat( this.Call.releasePerc.toFixed(2)))*100;
     console.log(this.Call)
       bar(parseInt((this.Call.releasePerc)));
     });

}




}







