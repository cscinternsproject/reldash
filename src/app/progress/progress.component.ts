import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.css']
})
export class ProgressComponent implements OnInit {

 Bar : any;

  constructor(private dataservice: ServiceService) { }

  ngOnInit(): void {


    this.dataservice.getProgress()
    .subscribe(data => {this.Bar = data;
   console.log(this.Bar)

    });
  }

}
