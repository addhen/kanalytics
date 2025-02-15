import UIKit
import KAnalyticsKt

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        configurationForConnecting connectingSceneSession: UISceneSession,
        options: UIScene.ConnectionOptions
    ) -> UISceneConfiguration {
        return KAnalyticsViewerShorcutHandlerKt.getUISceneConfiguration(configurationForConnectingSceneSession: connectingSceneSession)
    }
}
