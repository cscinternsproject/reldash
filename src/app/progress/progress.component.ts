import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.css']
})
export class ProgressComponent implements OnInit {

 public Data : any;

  constructor(private dataservice: ServiceService) { }

  ngOnInit(): void {


    this.dataservice.getProgress()
    .subscribe(data => {this.Data = data;
    this.Data.releasePerc=0.6;
    // if(this.Data.Capacty=='Red')
    // this.Data.Capacty='#d42626';
    //  if(this.Data.sprintColor=='Green')
    // this.Data.sprintColor='#54a84c';
    });
  }

}
