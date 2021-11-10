import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Lamb } from '../vo/Lamb';

@Component({
  selector: 'app-add-new-lamb',
  templateUrl: './add-new-lamb.component.html',
  styleUrls: ['./add-new-lamb.component.css']
})
export class AddNewLambComponent implements OnInit {

  form = new FormGroup({
    code: new FormControl('', [Validators.required, Validators.pattern("^[0-9]")]),
    color: new FormControl('', [Validators.required]),
    named: new FormControl('', [Validators.required]),
    dateOfMating: new FormControl('', [Validators.required]),
    weightOfBirth: new FormControl('', [Validators.required]),
    weightOfWeaning: new FormControl('', [Validators.required]),
    weightAtSale: new FormControl('', [Validators.required]),
    weigthOfSheep: new FormControl('', [Validators.required]),
    shape: new FormControl('', [Validators.required]),
    sheepDTO: new FormControl('', [Validators.required]),
    status: new FormControl('', [Validators.required]),
    advantages: new FormControl(),
    disadvantages: new FormControl(),
    type: new FormControl('', [Validators.required]),
    place: new FormControl(),
    notes: new FormControl(),
    birthDate: new FormControl('', [Validators.required]),
  });
  lamb: Lamb;
  validateAllForm = false;
  statuses: SelectItem[];
  sheeps: SelectItem[];
  types: SelectItem[];
  colors: SelectItem[];
  places: SelectItem[];
  @ViewChild('addForm') addNewLambForm: NgForm;
  constructor( 
    private router: Router,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService,
    private datePipe: DatePipe) {
    this.lamb = new Lamb();
   }
  ngOnInit(): void {
    const code = this.route.snapshot.queryParams.code;
    const color = this.route.snapshot.queryParams.color;
  
    if (code && color){
      const body = {
        code:  code,
        color: color,
      };
      this.generalService.getLambForEdit(body).subscribe(
        (data) => {
           this.lamb = data;
           this.sheeps.push({
            label: "code : "+this.lamb.sheepDTO.code +" , color : "+this.lamb.sheepDTO.color,
            value: this.lamb.sheepDTO
           });
           this.places.push({
            label: this.lamb.place.code,
            value: this.lamb.place
        });
           this.lamb.birthDate = this.lamb.birthDate != null ? new Date(this.lamb.birthDate) : null;
           this.lamb.dateOfMating = this.lamb.dateOfMating != null ? new Date(this.lamb.dateOfMating) : null;
          },(error) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          this.messageService.add({severity: 'error', detail: "هناك خطأ حاول مره اخرى  "});
         }
     );
    }
    this.getLambsMom();
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
      id: this.lamb.id,
      named: this.lamb.named,
      code: this.lamb.code,
      type: this.lamb.type,
      status: this.lamb.status,
      sheepDTO: this.lamb.sheepDTO,
      color: this.lamb.color,
      weightOfBirth: this.lamb.weightOfBirth,
      weightOfWeaning: this.lamb.weightOfWeaning,
      weightAtSale: this.lamb.weightAtSale,
      weigthOfSheep: this.lamb.weigthOfSheep,
      shape: this.lamb.shape,
      advantages: this.lamb.advantages,
      disadvantages: this.lamb.disadvantages,
      notes: this.lamb.notes,
      place:this.lamb.place,
      birthDate: this.datePipe.transform(this.lamb.birthDate, 'yyyy-MM-dd 00:00'),
      dateOfMating: this.datePipe.transform(this.lamb.dateOfMating, 'yyyy-MM-dd 00:00'),
                  };
    this.generalService.addLamb(model).subscribe(
                (response) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
                  this.addNewLambForm.reset();
                 },(error) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  console.log(error);
                  this.messageService.add({severity: 'error', detail: "هناك خطأ في الاضافة حاول مره اخرى"});
                 }
             );
  }
}


  cancel() {
    this.router.navigate(['/sheepframe/home/dashboard']);
  }

  statusLookups(){
      this.statuses = [ 
        {label: 'الحالة الاولى', value: 'first'},
        {label: 'الحالة الثانية', value: 'second'},
        ];
  }

  typeLookups(){
    this.types = [ 
      {label: 'فحل', value: 'ram'},
      {label: 'نعجة', value: 'ewe'}
      ];
  }
  colorLookups(){
    this.colors = [ 
      {label: 'أحمر', value: 'red'},
      {label: 'أصفر', value: 'yellow'},
      {label: 'أخضر', value: 'green'},
      ];
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

  getLambsMom() {
    this.sheeps = [];
    this.generalService.getEwes().subscribe(
        (responseData: any) => {
          console.log(responseData);
            for (let i = 0; i < responseData.length; i++) {
                this.sheeps.push({
                    label: "code : "+responseData[i].code +" , color : "+responseData[i].color,
                    value: responseData[i]
                });
            }
        },
        (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          console.log(error);
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل الامهات حاول مرة اخرى"});
        }
    );
}


}
