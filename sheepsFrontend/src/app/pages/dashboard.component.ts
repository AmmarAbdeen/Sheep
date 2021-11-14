import {Component, OnInit} from '@angular/core';
import {BreadcrumbService} from '../breadcrumb.service';
import {SelectItem} from 'primeng/primeng';
import {MenuItem} from 'primeng/primeng';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { Subscription } from 'rxjs';
import {ChartModule} from 'primeng/chart';

@Component({
    templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {

    data: any;

    subscription: Subscription;

    employeeOrgData; orgCount1;
    orgCount2; orgCount3; orgCount4; orgCount5; employeeLabel: any;
    employeeJIRAHoursData;
  
    
    employeeOrganizationData = [
      { name: 'employee1', designation: 'manager', employer: 'organization1' },
      { name: 'employee2', designation: 'manager', employer: 'organization1' },
      { name: 'employee3', designation: 'manager', employer: 'organization1' },
      { name: 'employee4', designation: 'manager', employer: 'organization1' },
      { name: 'employee5', designation: 'manager', employer: 'organization2' },
      { name: 'employee6', designation: 'manager', employer: 'organization2' },
      { name: 'employee7', designation: 'manager', employer: 'organization2' },
      { name: 'employee8', designation: 'manager', employer: 'organization3' },
      { name: 'employee9', designation: 'manager', employer: 'organization3' },
      { name: 'employee10', designation: 'manager', employer: 'organization3' },
      { name: 'employee11', designation: 'manager', employer: 'organization3' },
      { name: 'employee12', designation: 'manager', employer: 'organization3' },
      { name: 'employee13', designation: 'manager', employer: 'organization3' },
      { name: 'employee14', designation: 'manager', employer: 'organization3' },
      { name: 'employee15', designation: 'manager', employer: 'organization4' },
      { name: 'employee16', designation: 'manager', employer: 'organization4' },
      { name: 'employee17', designation: 'manager', employer: 'organization5' },
      { name: 'employee18', designation: 'manager', employer: 'organization5' },
      { name: 'employee19', designation: 'manager', employer: 'organization5' },
      { name: 'employee20', designation: 'manager', employer: 'organization5' },
      { name: 'employee21', designation: 'manager', employer: 'organization5' }
    ]

    constructor( private breadcrumbService: BreadcrumbService) {
       
    }

    ngOnInit() {

        this.orgCount1 = this.employeeOrganizationData.filter(emp => emp.employer === 'organization1').length;
        this.orgCount2 = this.employeeOrganizationData.filter(emp => emp.employer === 'organization2').length;
        this.orgCount3 = this.employeeOrganizationData.filter(emp => emp.employer === 'organization3').length;
        this.orgCount4 = this.employeeOrganizationData.filter(emp => emp.employer === 'organization4').length;
        this.orgCount5 = this.employeeOrganizationData.filter(emp => emp.employer === 'organization5').length;
    
        this.employeeLabel =
    
          this.employeeOrganizationData.map(emp => emp.employer)
            .filter((value, index, self) => self.indexOf(value) === index);

            
        this.data = {
            labels: this.employeeLabel,
            datasets: [
              {
                data: [this.orgCount1, this.orgCount2, this.orgCount3, this.orgCount4, this.orgCount5],
                backgroundColor: ['#ff0000', '#0000FF', '#FFFF00', '#FFC0CB', '#7f00ff ']
              }
            ]
          }


       
    }

   

   
}