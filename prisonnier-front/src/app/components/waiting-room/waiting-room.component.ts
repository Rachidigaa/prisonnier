import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../../services/game.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-waiting-room',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './waiting-room.component.html',
  styleUrl: './waiting-room.component.css'
})
export class WaitingRoomComponent {
  gameId!: number;
  playerId !: String;
  gameStatus: any;
  private intervalId: any; // Stocke la référence de setInterval

  constructor(
    private route: ActivatedRoute,
    private gameService: GameService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.gameId = Number(this.route.snapshot.paramMap.get('gameId'));
    this.playerId = this.route.snapshot.queryParamMap.get('playerId') || '';
    this.checkGameStatus();
    this.pollGameStatus(); // Démarre les vérifications périodiques
  }

  // Vérifie l'état actuel de la partie
  checkGameStatus(): void {
    this.gameService.getGameStatus(this.gameId).subscribe((status) => {
      this.gameStatus = status;
      if (status.status === 'STARTED') {
        this.stopPolling();
        this.router.navigate([`/game/${this.gameId}/${this.playerId}`]);
      }
    });
  }

  // Vérifie l'état toutes les 3 secondes
  pollGameStatus(): void {
    this.intervalId = setInterval(() => {
      this.checkGameStatus();
    }, 3000);
  }

  // Arrête le polling
  stopPolling(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId); // Arrête setInterval
      this.intervalId = null;
    }
  }

  // Nettoyage lorsque le composant est détruit
  ngOnDestroy(): void {
    this.stopPolling(); // Nettoie le setInterval lorsqu'on quitte le composant
  }

  // Quitter la partie
  quitGame(): void {
    alert('Vous avez quitté la partie.');
    this.stopPolling(); // Arrête le polling avant de quitter
    this.router.navigate(['/home']);
  }

  // Démarrer la partie (si nécessaire)
  startGame(): void {
    this.stopPolling(); // Arrête le polling avant de rediriger
    this.router.navigate(['/game', this.gameId]); // Redirige vers la page de jeu
  }

}
