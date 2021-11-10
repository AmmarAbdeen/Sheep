import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Place } from '../vo/Place';

@Component({
  selector: 'app-places',
  templateUrl: './places.component.html',
  styleUrls: ['./places.component.css']
})
export class PlacesComponent implements OnInit {
  form = new FormGroup({
    code: new FormControl('', [Validators.required]),
    capacity: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
   
});
cols: any[];
places: any[];
place : Place;
validateAllForm = false;
@ViewChild('addForm') savePlace: NgForm;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService
  ) {
    this.place = new Place();
   }

  ngOnInit(): void {
        this.cols = [
          { field: 'code', header: 'الكود' },
          { field: 'capacity', header: 'السعة' },
          { field: 'description', header: 'الوصف' },  
      ];

      this.generalService.getPlaces().subscribe(
        (responseData: any) => {
            this.places = responseData;
            if (!this.places.length) {
                this.messageService.clear();
                document.documentElement.scrollTop = 0;
                this.messageService.add({ severity: 'error', detail:"لا يوجد حظائر  " });
           } else {
                this.messageService.clear();
            }
       },
       (error: any) => {
                document.documentElement.scrollTop = 0;
                this.messageService.clear();
                this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد حظائر' });
                this.places = [];
           }
    );
  }

  onsubmit(){
        if (this.form.invalid) {
          this.validateAllForm = true;
          this.form.setErrors({ ...this.form.errors, yourErrorName: true });
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          this.messageService.add({ severity: 'error', detail: "من فضلك ادخل الحقول المطلوبة" });
          return;
        }else  {
            const model = {
              code: this.place.code,
              capacity: this.place.capacity,
              description: this.place.description
                };
        this.generalService.savePlace(model).subscribe(
                (response) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
                  this.savePlace.reset();
                 },(error) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  console.log(error);
                  this.messageService.add({severity: 'error', detail: "هناك خطأ في الاضافة حاول مره اخرى  "});
                 }
             );
  }
  }

  cancel() {
    this.router.navigate(['/sheepframe/home/dashboard']);
  }

}
