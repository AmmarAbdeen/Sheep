import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, TreeNode } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Privilege } from '../vo/Privilege';
import { User } from '../vo/User';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  form = new FormGroup({
    userName:new FormControl('', Validators.required),
    password:new FormControl('', [Validators.required, Validators.minLength(6)]),
    fullName:new FormControl('', Validators.required),
    nationalId:new FormControl(''),
    email:new FormControl('', [Validators.required,Validators.pattern("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")]),
    mobileNumber:new FormControl('', [Validators.required,Validators.pattern("^01[0125][0-9]{8}$")])
});
    user : User;
    validateAllForm = false;
    userInfo:any;
    userTree: TreeNode[];
    treeSelection: TreeNode[];
    selected: Privilege[];
    editMode = false;
    privileges: Privilege[];
    parentprivileges: Privilege[];
   @ViewChild('newUserForm') newUserForm: NgForm;
  constructor(private router: Router,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService) { 
      this.user = new User();
    }

  ngOnInit(): void {
    this.selected = [];
    this.treeSelection = [];
    this.userInfo = JSON.parse(localStorage.getItem('user'));
    this.route.queryParams.subscribe(params => {
        if (!params['id']) {
            this.setFormGroupControlsValsToEmpty();
            this.removeAllSelections();
            this.editMode = false;
        }
    });
    const id = this.route.snapshot.queryParams.id;
    if (id) {
        this.editMode=true;
        this.generalService.getUserById(id).subscribe(
            (data) => {
                this.user = data;
                for (let i in this.user.privileges) {
                    this.treeSelection.push(this.privilegeToNode(this.user.privileges[i]));
                    this.selected.push(this.user.privileges[i]);
                }
            },
            (error) => {
                    document.documentElement.scrollTop = 0;
                    this.messageService.clear();
                    this.messageService.add({ severity: 'error', summary: 'خطأ ', detail: 'هناك مشكلة في تحميل معلومات الموظف' });
            }
        );
    }
    this.getParentPrivileges();
    this.getAllPrivileges();
  }

  getAllPrivileges(){
            this.generalService.getAllPrivilegesByUser(this.userInfo.id).subscribe(
                    (data) => {
                        this.privileges = data;
                    },
                    (error) => {
                        this.messageService.clear();
                        this.messageService.add({ severity: 'error', detail: 'هناك مشكلة في تحميل الوظائف'});
                     
                    }
                );
  }

  getParentPrivileges(){
          this.generalService.getParentPrivilegesByUser(this.userInfo.id).subscribe(
        (data) => {
            this.userTree = [];
            this.parentprivileges = data;
            for (let i in this.parentprivileges) {
                    this.userTree.push(this.privilegeToNode(this.parentprivileges[i]));
                }
            },
        (error) => {
                this.messageService.clear();
                this.messageService.add({ severity: 'error', detail: 'هناك مشكلة في تحميل الوظائف'});
             
            }
       );
  }

  private privilegeToNode(privilege: Privilege): TreeNode {
      let privilegeNode: TreeNode = { children: [] };
      if (privilege.childrenPrivilege == null) {
           privilegeNode.label = privilege.arabicName; 
          privilegeNode.key = privilege.id.toString();
          privilegeNode.expandedIcon = "pi pi-folder-open";
          privilegeNode.collapsedIcon = "pi pi-folder";
          return privilegeNode;
      }
      else {
          privilegeNode.label = privilege.arabicName; 
          privilegeNode.key = privilege.id.toString();
          privilegeNode.expandedIcon = "pi pi-folder-open";
          privilegeNode.collapsedIcon = "pi pi-folder";
          for (let i in privilege.childrenPrivilege) {
              privilegeNode.children.push(this.privilegeToNode(privilege.childrenPrivilege[i]))
          }
          return privilegeNode;
      }
}

nodeUnSelect(event) {
      for (let i = 0; i < this.selected.length; i++) {
          if (this.selected[i].id == event.node.key) {
              this.selected.splice(i, 1);
          }
      }
      this.removeChildren(event.node.children);
      this.removeParents(event.node);
}

removeChildren(children: any) {
        if (children.length == 0)
            return;

        for (let child in children) {
            this.removeChildren(children[child].children);
            for (let i = 0; i < this.selected.length; i++) {
                if (this.selected[i].id == children[child].key) {
                    this.selected.splice(i, 1);
                    break;
                }
            }
        }
}

removeParents(node: TreeNode) {
        while (node.parent != null) {
            for (let i = 0; i < this.selected.length; i++) {
                if (Number(node.parent.key) === this.selected[i].id) {
                    this.selected.splice(i, 1);
                    break;
                }
            }
            node = node.parent;
        }
}


nodeSelect(event) {
        let nonSelectedHigherOrderPrivelege = this.nonSelectedHigherOrderPrivelege(event);
        if (nonSelectedHigherOrderPrivelege) {
            this.removeParentsPartialSelections(event.node);
            this.removeTreeSelections(event.node);

            if (this.treeSelection.length == 0) this.treeSelection = null;
            document.documentElement.scrollTop = 0;
            this.messageService.clear();
            this.messageService.add({ severity: 'error', detail: ` Error : ${nonSelectedHigherOrderPrivelege} is not selected` });
            return;
        }

        for (let i in this.privileges) {
            for (let j in this.treeSelection) {
                let exist = false;
                if (this.treeSelection[j].key == this.privileges[i].id.toString()) {
                    if (this.selected.length == 0) {
                        this.selected.push(this.privileges[i]);
                    }
                    else {

                        for (let k in this.selected) {
                            if (this.treeSelection[j].key == this.selected[k].id.toString()) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            this.selected.push(this.privileges[i]);

                        }
                    }
                }
                if (this.treeSelection[j].parent != null)
                    this.addParent(this.treeSelection[j])
            }

        }

}

nonSelectedHigherOrderPrivelege(event: any) {
  let parent = event.node.parent;
  let currentNodeOrder = this.getPrivilege(Number(event.node.key)).privilegeorder;
  while (parent) {
      for (let i = 0; i < parent.children.length; i++) {
          let child = parent.children[i];
          let privilegeChild = this.getPrivilege(child.key);
          if (privilegeChild.privilegeorder < currentNodeOrder && !this.isSelected(privilegeChild.id))
              return privilegeChild.englishName;
      }
      currentNodeOrder = this.getPrivilege(Number(parent.key)).privilegeorder;
      parent = parent.parent;
  }
  return;
}

getPrivilege(nodeKey: any) {
      for (let privilege of this.privileges) {
          if (nodeKey == privilege.id)
              return privilege;
      }
}

isSelected(privilegeChildId: any) {
      for (let privilege of this.selected) {
          if (privilegeChildId == privilege.id)
              return true;
      }
      return false;
}

addParent(node: TreeNode) {
  for (let i in this.privileges) {
      if (node.parent != null) {
          if (node.parent.key == this.privileges[i].id.toString()) {
              let exist = false;
              for (let k in this.selected) {
                  if (node.parent.key == this.selected[k].id.toString()) {
                      exist = true;
                  }
              }
              if (!exist) {
                  this.selected.push(this.privileges[i]);
                  this.addParent(node.parent)
              }
          }
      }
  }
}

removeParentsPartialSelections(node: any) {
  while (node.parent != null) {
      node.parent.partialSelected = false;
      node = node.parent;
  }
}

removeTreeSelections(node: any) {
  while (this.treeSelection.pop().label.localeCompare(node.label) != 0);
}

cancel() {
        this.router.navigate(['/home/dashboard']);
    
}

onSubmit() {
    this.user.privileges = [];
    
    if (this.form.invalid) {
        this.validateAllForm = true;
        this.form.setErrors({ ...this.form.errors, yourErrorName: true });
        document.documentElement.scrollTop = 0;
        this.messageService.clear();
        this.messageService.add({ severity: 'error', detail: 'من فضلك ادخل الحقول المطلوبة' });
        return;
    }
    else {
        if (this.selected.length == 0) {
            document.documentElement.scrollTop = 0;
            this.messageService.clear();
            this.messageService.add({ severity: 'error', detail: 'قوم باختيار وظيفة واحدة على الاقل' });
            return;
        }
        const id = this.route.snapshot.queryParams.id;
        const model = {
            id: this.user.id,
            userName: this.user.userName,
            email: this.user.email,
            mobileNumber: this.user.mobileNumber,
            nationalId:this.user.nationalId,
            fullName:this.user.fullName,
            privileges: this.selected,
            password:  this.user.password
        };
        
            if (id) {
                this.generalService.updateUser(model).subscribe(
                    (response) => {
                        document.documentElement.scrollTop = 0;
                        this.messageService.clear();
                        this.messageService.add({ severity: 'success', detail: 'تم حفظ البيانات' });
                        this.validateAllForm = false;
                        this.setFormGroupControlsValsToEmpty();
                        this.removeAllSelections();
                    },
                    (error) => {
                        document.documentElement.scrollTop = 0;
                        this.messageService.clear();
                        this.messageService.add({ severity: 'error', summary: 'خطأ : ', detail: 'هناك خطأ في التعديل ' });
                        }
                    
                );
            }
       else{
            this.generalService.newUser(model).subscribe(
                (response) => {
                    document.documentElement.scrollTop = 0;
                    this.messageService.clear();
                    this.messageService.add({ severity: 'success', detail: 'تم حفظ البيانات' });
                    this.validateAllForm = false;
                    this.setFormGroupControlsValsToEmpty();
                    this.removeAllSelections();
                },
                (error) => {
                        document.documentElement.scrollTop = 0;
                        this.messageService.clear();
                        this.messageService.add({ severity: 'error', summary: 'خطأ : ', detail: 'هناك خطأ في الاضافة ' });

                }
            );
        }}
    }

    setFormGroupControlsValsToEmpty() {
        const formVals = this.form.value;
        this.form.reset();
        for (let formVal in formVals) {
            this.form.patchValue({ [formVal]: '' });
        }
        console.log("form values:");
        console.log(this.form.value);
    }


    removeAllSelections() {
        for (let privilege in this.selected) {
            this.removeParentsPartialSelections(privilege);
        }
        this.selected = [];
        this.treeSelection = [];
    }

}




