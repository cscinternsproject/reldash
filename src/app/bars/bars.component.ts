import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';
import {Chart} from 'chart.js';


@Component({
  selector: 'app-bars',
  templateUrl: './bars.component.html',
  styleUrls: ['./bars.component.css']
})
export class BarsComponent implements OnInit {

  Bar : any;
  PieChart : [];

  constructor(private dataservice: ServiceService) { }

  ngOnInit(): void {

    this.PieChart = new Chart('pieChart', {
    type: 'pie',
    data: {
     labels: ["Developers", "Q/A"],
     datasets: [{
         data: [9,5],
         backgroundColor: [
             'red',
             'blue'
         ],


         borderWidth: 0.5
     }]
    },
    options: {
     title:{
         text:"Team Demographics",
         defaultFontSize:"200px",
         display:true
     }

    }
    });


    this.dataservice.getProgress()
    .subscribe(data => {this.Bar = data;
    //this.Bar.releasePerc=0.6;
     if(this.Bar.Capacty=='Red')
     this.Bar.Capacty='#d42626';
     if(this.Bar.sprintColor=='Green')
     this.Bar.sprintColor='#54a84c';
    });
  }


}
