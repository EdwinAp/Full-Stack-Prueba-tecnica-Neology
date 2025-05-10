import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, ViewChild, OnInit } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { VehiclesService } from '../../services/vehicles.service';
import { VehicleData } from '../../models/vehicle-data';
import { PageResponse } from '../../models/page-response';
import { Subject, takeUntil } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Router } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-list-vehicle',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    CommonModule,
    MatButtonModule,
    MatIcon,
    MatProgressSpinnerModule,
    MatSort,
    MatPaginator,
  ],
  templateUrl: './list-vehicle.component.html',
  styleUrl: './list-vehicle.component.scss'
})
export class ListVehicleComponent implements AfterViewInit, OnInit {

  isLoading: boolean = true

  length = 50;
  pageSize = 2;
  pageIndex = 0;
  pageSizeOptions = [2, 3, 5, 10, 25];

  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent: PageEvent | undefined;


  displayedColumns: string[] = ['plate', 'name', 'description', 'cost', 'actions'];
  dataSource = new MatTableDataSource<VehicleData>([]); 
  
  @ViewChild(MatSort) sort!: MatSort;

  pageResponse!: PageResponse<VehicleData>;

  private unsubscribe$ = new Subject<void>();

  constructor(
    private vehiclesService: VehiclesService,
    private router: Router
  ) { }

  ngOnInit() {
    this.isLoading = true
    this.pageableReques(this.pageIndex, this.pageSize);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;

    if (this.pageResponse) {
      this.setPaginatorValues();
    }

  }

  private setPaginatorValues() {
    this.length = this.pageResponse.totalElements ?? 0;
    this.pageSize = this.pageResponse.pageSize ?? 0;
    this.pageIndex = this.pageResponse.currentPage ?? 0;
  }

  private pageableReques(currentPage: number, pageSize: number): void {
    this.vehiclesService.pageableVehicles({
      currentPage: currentPage,
      pageSize: pageSize
    }).pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (data) => {
          this.pageResponse = data;
          this.dataSource.data = data.content ?? [];
          this.setPaginatorValues();
          this.isLoading = false
        },
        error: (ex) => {
          console.error(ex);
          this.dataSource.data = [];
        }
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  handlePageEvent(e: PageEvent) {
    this.isLoading = true
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.pageableReques(this.pageIndex, this.pageSize);
  }

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    if (setPageSizeOptionsInput) {
      this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
    }
  }

  onButtonClick(plate: string): void {
    this.router.navigate([`/detail-vehicle/${plate}`]);
  }
}
