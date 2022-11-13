I kept the app pretty simple. 

 I decided to use the latest stuff android offers, includes MVVM, android jetpack components & some
 third party libraries for testing. 
 
1. I used one activity instead of fragments since there is no two different screens like navigation 
   to a detail screen. So, I didn't see a point to use fragments. But wouldn't be a huge refactor anyway. 

2. Used flows instead of livedata although in this case may not have really needed to use since things
   are left pretty simple. But I'd imagine you might ask to do some fancy stuff during the code review 
   part so is an overall better option. 

3. DiffUtil for better adapter performance. 

4. Used extension function for alert in case we do end up doing some modifications later on.

5. I skipped out on some styling for TextView styles. I thought there wasn't some major styles
   I could think of. But is pretty easy really simply define them in a styles.xml & @styles/....
