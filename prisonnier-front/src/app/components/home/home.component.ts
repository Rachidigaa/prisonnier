import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ButtonModule } from 'primeng/button';
import { Listbox } from 'primeng/listbox';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent,ButtonModule,Listbox],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  rules: string[] = [
    'Si les deux joueurs coopèrent, ils obtiennent chacun 3 points.',
    'Si un joueur trahit et l’autre coopère, le traître obtient 5 points tandis que le coopérant n’en obtient aucun.',
    'Si les deux joueurs trahissent, ils reçoivent chacun 1 point.'
  ];
}
