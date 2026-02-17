**Atelier Eco Drive**

Pour que le projet fonctionne, mettez le port de mongodb à 27018

On utilises ici la base de donnée eco_drive_db

*Partie 1 : Modélisation Avancée (1h)*

Objectif : Concevoir une structure hybride (Imbrication vs Référence).

Chaque groupe doit modéliser un document Vehicle . Un véhicule possède :
- Des infos fixes (Marque, modèle, immatriculation).
- Une télémétrie imbriquée (dernière position GPS, niveau de batterie actuel).
- Un historique des incidents (tableau d'objets imbriqués : date, type, description).
- Une référence vers son propriétaire (Collection Users ).

Défi de modélisation : Comment stocker les caractéristiques techniques qui
varient selon le modèle (ex: autonomie, puissance moteur, options logicielles)
sans avoir de colonnes vides ?