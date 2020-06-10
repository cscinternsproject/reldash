import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';
declare const bar: any;

@Component({
  selector: 'app-head',
  templateUrl: './head.component.html',
  styleUrls: ['./head.component.css',
  '../../../node_modules/bootstrap/dist/css/bootstrap.min.css']
})
export class HeadComponent implements OnInit {
  public Data : any;
 

  constructor(private dataservice: ServiceService) { }



  ngOnInit(): void {

    this.dataservice.getParameter()
    .subscribe(data => {this.Data = data;
    this.date(this.Data);

    });
    
  }

  date(Data){

    Data.rendDate = (new Date(Data.rendDate)).toDateString();
    Data.rstartDate = new Date(Data.rstartDate).toDateString();
    Data.SstartDate =(new Date(Data.SstartDate)).toDateString();
    Data.SendDate = (new Date(Data.SendDate)).toDateString();
    Data.project ="Release Dashboard";
    Data.release ="Release1";
    Data.value=80;

    bar(80);

  }
}
