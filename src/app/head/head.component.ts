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
    console.log(data);
    this.date(this.Data);

    });
    
  }

  date(Data){

    Data.rendDate = (new Date(Data.rendDate)).toDateString();
    Data.rstartDate = new Date(Data.rstartDate).toDateString();
    Data.sstartDate =(new Date(Data.sstartDate)).toDateString();
    Data.sendDate = (new Date(Data.sendDate)).toDateString();
    Data.project ="Release Dashboard";
    //Data.release ="Release1";
    Data.releasePerc=(1-parseFloat( Data.releasePerc.toFixed(2)))*100;
  console.log(this.Data)
    bar(parseInt((Data.releasePerc)));

  }
}
