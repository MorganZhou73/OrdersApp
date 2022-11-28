import { TestBed } from '@angular/core/testing';
import { Title } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('AppComponent', () => {
  let titleService: Title;
  
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ RouterTestingModule ],
      declarations: [
        AppComponent
      ],
      providers: [Title],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'Orders System'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    
    expect(app.title).toEqual('Orders System');

    titleService = TestBed.inject(Title);
    
    expect(titleService.getTitle()).toBe(`Orders System`);
  });

  it('should render title', () => {    
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('span')?.textContent).toContain('Welcome To Orders System');
  });
});
