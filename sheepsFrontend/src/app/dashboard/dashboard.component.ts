import { Component, OnInit } from '@angular/core';
import { GeneralService } from '../service/general.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  data: any;
  dataForSheep: any;
  dataForLambs: any;
  dataForAmount: any;
  constructor(private generalService: GeneralService) { }

  ngOnInit(): void {
       this.getAllLambsGroupByType();
       this.getAllSheepGroupByType();
       this.getAllAmountOfStoredFeed();
  }

  getAllSheepGroupByType(){
        this.generalService.getAllSheepGroupByType().subscribe(
          (responseData: any) => {
              console.log(responseData);
              this.dataForSheep = {
                  labels: responseData.labels,
                  datasets: [
                      {
                          data: responseData.data,
                          backgroundColor: [
                              "#42A5F5",
                              "#66BB6A"
                          ],
                          hoverBackgroundColor: [
                              "#64B5F6",
                              "#81C784"
                          ]
                      }
                  ]
              };
          },
          (error: any) => {
            console.log(error);
          }
      
      );

  }

  getAllLambsGroupByType(){
          this.generalService.getAllLambsGroupByType().subscribe(
            (responseData: any) => {
                console.log(responseData);
                this.dataForLambs = {
                    labels: responseData.labels,
                    datasets: [
                        {
                            data: responseData.data,
                            backgroundColor: [
                                "#42A5F5",
                                "#66BB6A"
                            ],
                            hoverBackgroundColor: [
                                "#64B5F6",
                                "#81C784"
                            ]
                        }
                    ]
                };
            },
            (error: any) => {
              console.log(error);
            }
        
        );
  }

  getAllAmountOfStoredFeed(){
        this.generalService.getAllAmountOfStoredFeed().subscribe(
          (responseData: any) => {
              console.log(responseData);
              this.dataForAmount = {
                  labels: responseData.labels,
                  datasets: [
                    {
                        label: 'Amount',
                        backgroundColor: '#42A5F5',
                        data: responseData.data
                    }
                ]
              };
          },
          (error: any) => {
            console.log(error);
          }
      
      );
  }

}
