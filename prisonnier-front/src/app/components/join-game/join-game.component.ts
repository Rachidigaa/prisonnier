import { Component } from '@angular/core';
import { GameService } from '../../services/game.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-join-game',
  standalone: true,
  imports: [FormsModule, ButtonModule, CommonModule],
  templateUrl: './join-game.component.html',
  styleUrl: './join-game.component.css'
})
export class JoinGameComponent {
  gameId!: number;
  playerName: string = '';

  constructor(private gameService: GameService, private router: Router) {}

  // Logique pour rejoindre une partie
  onJoinGame() {
    this.gameService.joinGame(this.gameId, this.playerName).subscribe(
      (response) => {
        if (response) {
          console.log(response)
          this.router.navigate([`/game/${this.gameId}/${response.players[1].id}`]); // Redirige vers une page vide pour le moment
        } else {
          alert("Impossible de rejoindre la partie. Vérifiez l'ID ou l'état de la partie.");
        }
      },
      (error) => {
        alert("Une erreur s'est produite lors de la tentative de rejoindre la partie.");
        console.error(error);
      }
    );
  }

  // Quitter l'action
  onQuit() {
    this.router.navigate(['/home']); // Retour à la page d'accueil
  }

}
