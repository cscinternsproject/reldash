import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';
import {Chart} from 'chart.js';
import {BehaviorSubject} from 'rxjs';


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


    this.dataservice.currentMessage.subscribe(message => {

      this.Bar = message;

    });

    //team demographic pie chart initialization
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



  }


}
