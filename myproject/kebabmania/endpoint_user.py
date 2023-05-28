import json
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt


@csrf_exempt
def editarborrar(request, tlf):
    if tlf == None:
        return JsonResponse({"error": "Missing a param"}, status=400)

    request_token = request.META.get('HTTP_SESSION_TOKEN')
    if request_token is None:
        return JsonResponse({"error": "Unauthorized"}, status=401)


    try:
        user_row = Progenitor.objects.get(token= request_token)
    except Progenitor.DoesNotExist:
        return JsonResponse({"error": "Unauthorized"}, status=401)