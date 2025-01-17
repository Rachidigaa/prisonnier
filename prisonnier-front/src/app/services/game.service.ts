import { Strategie, Game, Decision, Round } from './../model/model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

export interface JoinGameResponse {
  game: Game;
  playerId: number;
}

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private apiUrl = environment.API_URL;

  constructor(private http: HttpClient) {}

  createGame(totalRounds: number): Observable<Game> {
    const params = new HttpParams().set('totalRounds', totalRounds.toString());
    return this.http.post<Game>(`${this.apiUrl}/create`, {}, { params }).pipe(
      tap({
        next: (response) => console.log('createGame Response:', response),
        error: (error) => console.error('createGame Error:', error),
      })
    );
  }

  joinGame(gameId: number, username: string): Observable<JoinGameResponse> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('username', username);
    return this.http.post<JoinGameResponse>(`${this.apiUrl}/join`, {}, { params }).pipe(
      tap({
        next: (response) => console.log('joinGame Response:', response),
        error: (error) => console.error('joinGame Error:', error),
      })
    );
  }

  playRound(gameId: number, playerId: number, decision: Decision): Observable<Round> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('playerId', playerId.toString())
      .set('decision', decision);
    return this.http.post<Round>(`${this.apiUrl}/play`, {}, { params }).pipe(
      tap({
        next: (response) => console.log('playRound Response:', response),
        error: (error) => console.error('playRound Error:', error),
      })
    );
  }

  abandonGame(gameId: number, playerId: number, strategy: Strategie): Observable<void> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('playerId', playerId.toString())
      .set('strategy', strategy);
    return this.http.post<void>(`${this.apiUrl}/abandon`, {}, { params }).pipe(
      tap({
        next: () => console.log(`abandonGame Success: gameId=${gameId}, playerId=${playerId}`),
        error: (error) => console.error('abandonGame Error:', error),
      })
    );
  }

  getGameStatus(gameId: number, playerId: number): Observable<Game> {
    const params = new HttpParams().set('requestingPlayerId', playerId.toString());
    return this.http.get<Game>(`${this.apiUrl}/${gameId}/status`, { params }).pipe(
      tap({
        next: (response) => console.log('getGameStatus Response:', response),
        error: (error) => console.error('getGameStatus Error:', error),
      })
    );
  }
  
  getStrategies(): Observable<Strategie[]> {
    return this.http.get<Strategie[]>(`${this.apiUrl}/strategies`).pipe(
      tap({
        next: (response) => console.log('getStrategies Response:', response),
        error: (error) => console.error('getStrategies Error:', error),
      })
    );
  }
}
