import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Sheep } from '../vo/Sheep';

@Component({
  selector: 'app-add-new-sheep',
  templateUrl: './add-new-sheep.component.html',
  styleUrls: ['./add-new-sheep.component.css']
})
export class AddNewSheepComponent implements OnInit {

    form = new FormGroup({
    code: new FormControl('', [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)?$/)]),
    color: new FormControl('', [Validators.required]),
    named: new FormControl('', [Validators.required]),
    // age: new FormControl('', [Validators.required]),
    weight: new FormControl('', [Validators.required]),
    shape: new FormControl('', [Validators.required]),
    status: new FormControl('', [Validators.required]),
    advantages: new FormControl(),
    disadvantages: new FormControl(),
    type: new FormControl('', [Validators.required]),
    place: new FormControl(),
    notes: new FormControl(),
    birthDate: new FormControl('', [Validators.required]),
    arrivalDate: new FormControl('', [Validators.required]),
});
  sheep: Sheep;
  oldSheep : Sheep;
  validateAllForm = false;
  editMode = false;
  statuses: SelectItem[];
  types: SelectItem[];
  colors: SelectItem[];
  places: SelectItem[];
  @ViewChild('addForm') addNewSheepForm: NgForm;
  constructor( 
    private router: Router,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService,
    private datePipe: DatePipe) {
    this.sheep = new Sheep();
   }

  ngOnInit(): void {

    const code = this.route.snapshot.queryParams.code;
    const color = this.route.snapshot.queryParams.color;
  
    if (code && color){
      this.editMode = true;
      const body = {
        code:  code,
        color: color,
      };
      this.generalService.getSheepForEdit(body).subscribe(
        (data) => {
           this.sheep = data;
           this.places.push({
            label: this.sheep.place.code,
            value: this.sheep.place
        });
        this.colors.push({
          label: this.sheep.color,
          value: this.sheep.color
      });
           this.sheep.birthDate = this.sheep.birthDate != null ? new Date(this.sheep.birthDate) : null;
           this.sheep.arrivalDate = this.sheep.arrivalDate != null ? new Date(this.sheep.arrivalDate) : null;
          },(error) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          this.messageService.add({severity: 'error', detail: "هناك خطأ حاول مره اخرى  "});
         }
     );
    }
    this.statusLookups();
    this.typeLookups();
    this.colorLookups();
    this.placeLookups();
  }

  onSubmit(){

    if (this.form.invalid) {
      this.validateAllForm = true;
      this.form.setErrors({ ...this.form.errors, yourErrorName: true });
      document.documentElement.scrollTop = 0;
      this.messageService.clear();
      this.messageService.add({ severity: 'error', detail: "من فضلك ادخل الحقول المطلوبة" });
      return;
  }else  {
    const model = {
      id: this.sheep.id,
      named: this.sheep.named,
      code: this.sheep.code,
      type: this.sheep.type,
      status: this.sheep.status,
      color: this.sheep.color,
      // age: this.sheep.age,
      weight: this.sheep.weight,
      shape: this.sheep.shape,
      advantages: this.sheep.advantages,
      disadvantages: this.sheep.disadvantages,
      notes: this.sheep.notes,
      place:this.sheep.place,
      birthDate: this.datePipe.transform(this.sheep.birthDate, 'yyyy-MM-dd 00:00'),
      arrivalDate: this.datePipe.transform(this.sheep.arrivalDate, 'yyyy-MM-dd 00:00'),
                  };
    this.generalService.addSheep(model).subscribe(
                (response) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
                  this.addNewSheepForm.reset();
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
    this.router.navigate(['/home/dashboard']);
  }

  statusLookups(){
      this.statuses = [ 
        {label: 'مباع', value: 'saled'},
        {label: 'متاح', value: 'available'}
        ];
  }

  typeLookups(){
    this.types = [ 
      {label: 'فحل', value: 'ram'},
      {label: 'نعجة', value: 'ewe'}
      ];
  }
  colorLookups(){
    this.colors =[];
    this.generalService.getLookupsByType("color").subscribe(
      (responseData: any) => {
          for (let i = 0; i < responseData.length; i++) {
              this.colors.push({
                  label: responseData[i].nameAR,
                  value: responseData[i].nameAR
              });
          }
      },
      (error: any) => {
        document.documentElement.scrollTop = 0;
        this.messageService.clear();
        console.log(error);
        this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل الحظائر حاول مرة اخرى"});
      }
  );
  }
  placeLookups(){
    this.places = [];
    this.generalService.getPlaces().subscribe(
        (responseData: any) => {
            for (let i = 0; i < responseData.length; i++) {
                this.places.push({
                    label: responseData[i].code,
                    value: responseData[i]
                });
            }
        },
        (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          console.log(error);
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل الحظائر حاول مرة اخرى"});
        }
    );
  }

}
