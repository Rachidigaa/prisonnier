import { Component } from '@angular/core';
import { GameService } from '../../services/game.service';
import { Router } from '@angular/router';
import { FormsModule, NgModel } from '@angular/forms';

@Component({
  selector: 'app-create-game',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './create-game.component.html',
  styleUrl: './create-game.component.css'
})
export class CreateGameComponent {
  playerName: string = '';
  rounds: number | null = null;

  constructor(private gameService: GameService, private router: Router) {}

  // Méthode appelée lors de la création de la partie
  onCreateGame() {
    if (this.playerName && this.rounds) {
      this.gameService.createGame(this.playerName, this.rounds).subscribe(
        (response) => {
          const playerid = response.players[0].id;
          console.log(playerid)
          // Inclure le nom du joueur dans la navigation vers la salle d'attente
          this.router.navigate([`/waiting-room/${response.id}`], {
            queryParams: { playerId: playerid },
          });
        },
        (error) => {
          alert('Une erreur s’est produite lors de la création de la partie.');
          console.error(error);
        }
      );
    }
  }
  
  

  // Méthode appelée lors du clic sur "Quitter"
  onQuit() {
    this.router.navigate(['/']); // Retour à la page d'accueil
  }

}
