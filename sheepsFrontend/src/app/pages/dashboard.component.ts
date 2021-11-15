import {Component, OnInit} from '@angular/core';
import {BreadcrumbService} from '../breadcrumb.service';
import {SelectItem} from 'primeng/primeng';
import {MenuItem} from 'primeng/primeng';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';

@Component({
    templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {



    data: any;


    constructor( ) {
       
    }

    ngOnInit() {
        this.data = {
            labels: ['x','y','z'],
            datasets: [
                {
                    data: [10,30,15],
                    backgroundColor: [
                        "#42A5F5",
                        "#66BB6A",
                        "#FFA726"
                    ],
                    hoverBackgroundColor: [
                        "#64B5F6",
                        "#81C784",
                        "#FFB74D"
                    ]
                }
            ]
        };
    }
}
