import SwiftUI
import shared
import UIKit


struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()//.ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



// //
// //struct ContentView: View {
// //	let greet = Greeting().greet()
// //
// //	var body: some View {
// //		Text(greet)
// //	}
// //}
// //
// //struct ContentView_Previews: PreviewProvider {
// //	static var previews: some View {
// //		ContentView()
// //	}g
// //}
//
//
// struct ContentView: UIViewControllerRepresentable {
//     func makeUIViewController(context: Context) -> UIViewController {
//         return Greeting().MainViewController() // استدعاء الدالة Kotlin لإنشاء UIViewController
//     }
//
//     func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
// }
//
// struct ContentView_Previews: PreviewProvider {
//     static var previews: some View {
//         ContentView()
//     }
// }
