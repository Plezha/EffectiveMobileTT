В целом, приложение сделано по [ТЗ](https://docs.google.com/document/d/15MkPXaen2j5oe-8ViUUnondUtGN6fnHCZnVnrmTmMnY/edit?tab=t.0#heading=h.zfm97ynodl5).  
Если не получается забилдить и воспользоватсья, можно написать [мне](https://t.me/plezhaa).  

## Использованный стек технологий
- Kotlin 
- Корутины и Flow
- Dagger + Hilt
- Retrofit
- Верстка обычная на XML с использвоанием AdapterDelegates
- MVVM + Clean Architecture
- Многомодульность

## В рамках многомодульности реализованы gradle-модули:
- ```:app``` - сочетает в себе feature-модули
- ```:feature:feature_search``` - реализует экран поиска 
- ```:feature:feature_favorites``` - реализует экран избранного
- ```:feature:feature_wip_fragment``` - реализует экраны-заглушки
- ```:common:ui``` - содержит в себе общие ui элементы, стили и т.п., котороые используют feature-модули
- ```:data``` - содержит в себе интерфейс единственного репозитория всего приложения и реализует его. 
Возможно, следовало бы сделать два модуля: ```:data:vacancies``` и ```:data:offers```, но текущая версия бэкенда этого не позволяет сделать напрямую.  
Также из него можно было бы выделить ```:domain``` модуль для более полного соответствия Clean Architecture, но пока что тяжело представить, какая логика там может быть.


## Приложение содержит 2 экрана, не являющихся заглушками 
- экран поиска  
<img src="https://github.com/user-attachments/assets/39f61f30-7189-4612-82be-cc260336d5b9" alt="drawing" width="200"/> <img src="https://github.com/user-attachments/assets/d969f5e0-7244-44bb-9954-d9017b7bd795" alt="drawing" width="200"/>  
- экран избранного  
<img src="https://github.com/user-attachments/assets/90d667fc-71e3-47af-9381-64915d4ccd85" alt="drawing" width="200"/> <img src="https://github.com/user-attachments/assets/cebc57f9-2bb4-4a00-8ebd-b5a52bd51abc" alt="drawing" width="200"/>  
