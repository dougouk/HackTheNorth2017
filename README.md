# HackTheNorth2017

## Inspiration
The main inspiration behind the whole app were those awkward moments when you go for coffee runs, and someone ends up complaining how they weren’t aware of the fact you actually went for a coffee run in the first place.

## What it does
Offers a plethora of features but the basis of the app is to send push notification to friends in your location radius to notify them of coffee runs and providing a safe online way of accepting and sending money to friends. For the future we plan to allow online menus that friends can select items to buy from. Usage of bitcoins is a key feature for those transactions. We believe that the world is moving towards a decentralized system of payment such as a bitcoin and we want to make sure we keep up with the times by allowing such a facility.

## How we built it
The app his made on the android platform, where the backend has been handled by Google's Firebase API. For transactions, we are using bitcoins as the currency and it is supported using the Coinbase API. To connect the Coinbase API to our application, it is implemented in Firebase Functions. We also utilize the Firebase real-time database to store data such as user, user groups, and order data from the application.

## Challenges we ran into
Like all great projects, we ended up with quite some challenges on our hands. Due to our limited experience with the Firebase and Coinbase APIs, we essentially started with no prior knowledge, which made it difficult to utilize them. There was also a lack of documentation regarding the API’s and their support for Android development.

## Accomplishments that we’re proud of
Taking up a very ambitious project to utilize features we had ver limited knowledge of, and being able to learn and go so far with it, is something we’re immensely proud off. We were able to utilize Firebase very well and integrated many of its features into our application. The biggest challenge being using Firebase's cloud functions feature to run a Node.js script on the backend to bridge the gap between Coinbase, Firebase and Android. Coinbase has very limited support for Android and Java, so by being able to connect the two, it meant a huge accomplishment for our team.

## What we learned
Coinbase as amazing as a company that it may be, it does not provide enough documentation for its API, making it extremely hard to use it. We also learned about the various functionality of Firebase, it was surprising to see the amount of work it can help us with, and it is definitely a useful service that we will use again in the future. Lastly, this hackathon was also the first time that we touched non-relational databases, this provided a new way of thinking

## What’s next for Coffee Time
The next big things would be 1) To utilize the location of users to detect nearby coffee stores and automatically import the menus of these coffee stores into the application. This would allow easier usage of this application.

2)We also want to use the location to limit the friends who receive a notification, so someone in Toronto does not request someone in Waterloo to pick up a coffee. Once we have these features set up, we can further expand the application to other services, such as food or groceries.

3) Create an incentive-reward model for coffee runners to encourage more people to make coffee runs than ask for coffee runs; such as free coffee for runners after every two runs.

The motto for us is "friends helping friends", we hope that by doing so, we can create a network effect which allows easy acquisition of active users.
